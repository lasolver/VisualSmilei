package com.github.tubus.visual.smilei;

import com.github.tubus.visual.smilei.service.SSHService;
import com.github.tubus.visual.smilei.service.SSHServiceJSH;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.jupiter.api.Test;
import java.io.File;

public class SSHServiceTest {

    SSHService sshService = new SSHServiceJSH();

    @Test
    public void testPythonExecution() throws JSchException {
        Session session = sshService
                .createSession("solver", "solver1", 22, "TODO"); // FIXME

        sshService.runPythonScript(
                "f = open(\"guru99.txt\",\"w+\")\n" +
                "f.write(\"This is line 1\\r\\n\")\n" +
                "f.close()", session, new File("/home/solver/disk/prokudn/Smilei"));
    }
}