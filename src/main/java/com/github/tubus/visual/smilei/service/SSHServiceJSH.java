package com.github.tubus.visual.smilei.service;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
@Slf4j
public class SSHServiceJSH implements SSHService {

    private static final int SSH_SESSION_DEFAULT_TIMEOUT = 30000;

    @Override
    public Session createSession(String user, String host, int port, String password) throws JSchException {
        JSch jsch = new JSch();

        String systemUserName = System.getProperty("user.name");
        File knownHosts = new File("/home/" + systemUserName + "/.ssh/known_hosts");
        if (knownHosts.exists()) {
            jsch.setKnownHosts(knownHosts.getAbsolutePath());
        }

        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(SSH_SESSION_DEFAULT_TIMEOUT);
        return session;
    }

    @Override
    public Boolean runPythonScript(String pythonScript, Session session, File externalDirectory) {
        pythonScript = "import os;\nos.chdir(\"" + externalDirectory.getAbsolutePath() + "\")\n" + pythonScript;

        if (!session.isConnected()) {
            try {
                session.connect();
            } catch (JSchException jschException) {
                log.error("Не удалось открыть соединение", jschException);
                return false;
            }
        }

        try {
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.put(new ByteArrayInputStream(pythonScript.getBytes()), externalDirectory.getAbsolutePath() + "/tmp_script.py");
            sftp.disconnect();
        } catch (JSchException jschException) {
            log.error("Не удалось открыть SFTP канал", jschException);
            return false;
        } catch (SftpException jschException) {
            log.error("Ошибка копирования скрипта на сервер", jschException);
            return false;
        }
        ChannelExec channelExec = null;
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand("python2.7 " + externalDirectory.getAbsolutePath() + "/tmp_script.py");
            channelExec.connect();
            StringBuilder builder = new StringBuilder();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    builder.append(new String(tmp, 0, i));
                }
                if (channelExec.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    log.info("exit-status: " + channelExec.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            log.debug(builder.toString());
            channelExec.disconnect();
            session.disconnect();
            return true;
        } catch (JSchException | IOException jschException) {
            log.error("Ошибка запуска скрипта на сервере", jschException);
            return false;
        }
    }
}