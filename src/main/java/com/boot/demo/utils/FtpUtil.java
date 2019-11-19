package com.etc.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * ftp工具类
 *
 * @author wu
 *         2018年8月8日17:06:04
 */
public class FtpUtil {

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 从SFTP服务器下载文件
     *
     * @param ftpHost     SFTP IP地址
     * @param ftpUserName SFTP 用户名
     * @param ftpPassword SFTP用户名密码
     * @param ftpPort     SFTP端口
     * @param ftpPath     SFTP服务器中文件所在路径 格式：
     * @param localPath   下载到本地的位置 格式：H:/
     * @param date        文件名称
     * @throws IOException
     * @throws SftpException
     */
    @SuppressWarnings("rawtypes")
    public static String downloadSftpFile(String ftpHost, String ftpUserName, String ftpPassword,
                                          int ftpPort, String ftpPath, String localPath, Date date) {
        String fileName = "";//f返回文件名称
        try {
//			Date date = dateTime.toDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileNameEnd = sdf.format(date);
            Session session = null;

            JSch jsch = new JSch();
            session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
            session.setPassword(ftpPassword);
            session.setTimeout(100000);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            logger.info("FtpUtil.downloadSftpFile.session =====>");

            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            Vector vector = channel.ls(ftpPath);
            Iterator it = vector.iterator();
            while (it.hasNext()) {
                String fileName1 = ((ChannelSftp.LsEntry) it.next()).getFilename();
                if (fileName1.contains(fileNameEnd)) {//验证服务器文件名是否为今天的日期
                    fileName = fileName1;
                    String ftpFilePath =
                            (ftpPath + fileName).trim().toString();
                    ChannelSftp chSftp = (ChannelSftp) channel;
                    chSftp.get(ftpFilePath, localPath);

                    logger.info("FtpUtil.downloadSftpFile.chSftp =====>");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


//	 public static void main(String[] args) throws IOException, SftpException {
//		 String ftpHost = "122.224.230.26";
//		 String ftpUserName = "KWL00HYD";
//		 String ftpPassword = "KWL00HYD";
//		 int ftpPort = 20028;
//		 String ftpPath = "/";
//		 String localPath = "E:/etc/zhegao/flow";
//
//		 System.out.println(downloadSftpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath,
//				 localPath,DateTime.now()));
//		 
//	 }


    /**
     * FTP单个文件下载
     *
     * @param ftpUrl       ftp地址
     * @param userName     ftp的用户名
     * @param password     ftp的密码
     * @param directory    要下载的文件所在ftp的路径名不包含ftp地址
     * @param destFileName 要下载的文件名
     * @param downloadName 下载后锁存储的文件名全路径
     */
    public static boolean download(String ftpUrl, String userName, String password, int port,
                                   String directory, String destFileName, String downloadName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        boolean result = false;
        try {
            ftpClient.connect(ftpUrl, port);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.changeWorkingDirectory(directory);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            System.out.println("destFileName:" + destFileName + ",downloadName:" + downloadName);
            result = ftpClient.retrieveFile(destFileName, new FileOutputStream(downloadName));
            return result;
        } catch (NumberFormatException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }

    /**
     * FTP获取对应文件文件夹下文件列表
     *
     * @param ftpUrl    ftp地址
     * @param userName  ftp的用户名
     * @param password  ftp的密码
     * @param directory 要下载的文件所在ftp的路径名不包含ftp地址
     * @param var       该目录下文件所包含的字段
     * @return
     * @throws IOException
     */
    public static List<String> findQualifiedFileName(String ftpUrl, String userName, String password, int port,
                                                     String directory, String... var) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpUrl, port);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.changeWorkingDirectory(directory);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (null == ftpFiles && ftpFiles.length <= 0) {
                return null;
            } else {
                List<String> list = new ArrayList<String>();
                for (FTPFile ftpFile : ftpFiles) {
                    String name = ftpFile.getName();
                    boolean isContains = true;
                    for (String s : var) {
                        boolean contains = name.contains(s);
                        if (!contains) {
                            isContains = false;
                            break;
                        }
                    }
                    if (isContains) {
                        list.add(name);
                    }
                }
                return list;
            }
        } catch (NumberFormatException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }

    public static void main(String[] args) throws IOException, SftpException {
        String ftpHost = "58.42.241.173";
        String ftpUserName = "test";
        String ftpPassword = "test";
        int ftpPort = 1021;
        String ftpPath = "/ConsumptionFiles";
        String localPath = "d:test/gg";
        Date date = new Date();
        downloadSftpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath,
                localPath, date);

    }


}
