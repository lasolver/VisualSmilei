package com.github.tubus.visual.smilei.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;

/**
 * Service for ssh connection to linux server
 *
 * @author mujum
 */
public interface SSHService {

    /**
     * Create ssh-session with provided authentication data
     *
     * @param user - Remote user name
     * @param host - Remote host name
     * @param port - Remote ssh port
     * @param password - Password for authentication
     *
     * @return Session
     * @throws JSchException - Exception on session creation
     */
    Session createSession(String user, String host, int port, String password) throws JSchException;

    /**
     *
     * @param pythonScript
     * @param session
     * @param externalDirectory
     * @return
     */
    Boolean runPythonScript(String pythonScript, Session session, File externalDirectory);
}