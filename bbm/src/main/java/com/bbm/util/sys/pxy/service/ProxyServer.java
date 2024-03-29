package com.bbm.util.sys.pxy.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.bbm.cmm.EgovWebUtil;
import com.bbm.util.sys.pxy.service.impl.ProxySvcDAO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

public class ProxyServer extends Thread {

    ProxySvcDAO proxySvcDAO;
    EgovIdGnrService egovProxyLogIdGnrService;

    ServerSocket serverSocket = null;
    Socket client = null;
    Socket server = null;

    private String svcIp = null;
    private String localIp = null;
    private int localPort;
    private int remotePort;
    private String threadName = null;

    DataInputStream disReader;
    DataOutputStream dosWriter;

    byte[] request = new byte[1024];
    byte[] reply = new byte[4096];

    ProxyLog proxyLog = null;

	public ProxyServer(String svcHost, String localIp, int localPort, int remotePort, String threadName,
			           ProxySvcDAO proxySvcDAO, EgovIdGnrService egovProxyLogIdGnrService) {

        try {
        	setSvcIp(svcHost);
        	setLocalIp(localIp);
        	setLocalPort(localPort);
        	setRemotePort(remotePort);
        	setThreadName(threadName);

        	this.proxySvcDAO = proxySvcDAO;
        	this.egovProxyLogIdGnrService = egovProxyLogIdGnrService;

        	serverSocket = new ServerSocket(localPort);

        } catch (Exception e) {
    	    //e.printStackTrace();
    	    throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
        }
	}

    public void run() {
    	try {
    		runServer();
    	} catch(Exception e) {
    		//e.printStackTrace();
    	    throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
    	}
    }

    public void runServer() throws IOException {

        boolean runningThread = true;

        try {
            while(runningThread) {

            	try {
                	serverSocket.setSoTimeout(2000);
                	System.out.println("client wait......");

                	client = serverSocket.accept();

            	} catch (Exception ce)	{
					    //ce.printStackTrace();
					    System.out.println(ce);	// 2011.10.10 보안점검 후속조치
						continue;
					}

            	if(client.isConnected()) {

            		insertProxyLog();

	            	System.out.println("client connect");
	                InputStream streamFromClient = client.getInputStream();
	                OutputStream streamToClient = client.getOutputStream();

	                try {
	                    server = new Socket(getSvcIp(), remotePort);
	                } catch(Exception ex) {
	                	//ex.printStackTrace();
	        			System.out.println(ex);	// 2011.10.10 보안점검 후속조치
	                }

	                InputStream streamFromServer = server.getInputStream();
	                OutputStream streamToServer = server.getOutputStream();

	                ProxyThread proxyThread = new ProxyThread(client, streamFromClient, streamToClient, streamFromServer, streamToServer);
	                Thread thread = new Thread(proxyThread, getThreadName()+"-"+server.getLocalPort());
	                thread.start();

	                int bytesRead;
	                try {
	                    while ((bytesRead = streamFromServer.read(reply)) != -1) {
	                        streamToClient.write(reply, 0, bytesRead);
	                        streamToClient.flush();
	                    }
	                } catch (IOException e) {
	            	    //e.printStackTrace();
	                	System.out.println(e);	// 2011.10.10 보안점검 후속조치
	                } finally {
	                    streamToClient.close();
	                    if(proxyThread.getIsStop()) {
	    				    runningThread = false;
	    					break;
	    				}
	                }
            	}
            }
        }
        catch (Exception e)
        {
    	    //e.printStackTrace();
        	System.out.println(e);	// 2011.10.10 보안점검 후속조치
        }
        finally {
			try
			{
				server.shutdownOutput();
				client.shutdownOutput();
				server.close();
				client.close();
				serverSocket.close();
			}
			catch (IOException ie)
			{
				//ie.printStackTrace();
				System.out.println(ie);	// 2011.10.10 보안점검 후속조치
			}
        }
    }

    public void insertProxyLog() {

		try {

    		proxyLog = new ProxyLog();
    		System.out.println("-------------------------------------------- 1");
    		proxyLog.setProxyId(getThreadName());
    		System.out.println("-------------------------------------------- 2");
    		proxyLog.setLogId(egovProxyLogIdGnrService.getNextStringId());
    		System.out.println("-------------------------------------------- 3");
    		if (!EgovWebUtil.isIPAddress((client.getInetAddress().getHostAddress()))) {	// 2011.10.25 보안점검 후속조치
    		    throw new RuntimeException("IP is needed. (" + client.getInetAddress().getHostAddress() + ")");
    		}
    		proxyLog.setClntIp(client.getInetAddress().getHostAddress());
    		System.out.println("-------------------------------------------- 4");
    		proxyLog.setClntPort(String.valueOf(getLocalPort()));
    		System.out.println("-------------------------------------------- 5");
    		proxyLog.setFrstRegisterId("SYSTEM");
    		proxyLog.setLastUpdusrId("SYSTEM");

    		System.out.println(proxyLog.getProxyId());
    		System.out.println(proxyLog.getLogId());
    		System.out.println(proxyLog.getClntIp());
    		System.out.println(proxyLog.getClntPort());
    		System.out.println(proxyLog.getFrstRegisterId());
    		System.out.println(proxyLog.getLastUpdusrId());

    		proxySvcDAO.insertProxyLog(proxyLog);

		} catch(Exception e) {
			System.out.println("proxyLog Insert Error : "+e);
		}
    }

    /**
	 * @return the svcIp
	 */
	public String getSvcIp() {
		return svcIp;
	}

	/**
	 * @param svcIp the svcIp to set
	 */
	public void setSvcIp(String svcIp) {
		this.svcIp = svcIp;
	}

	/**
	 * @return the localIp
	 */
	public String getLocalIp() {
		return localIp;
	}

	/**
	 * @param localIp the localIp to set
	 */
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	/**
	 * @return the localPort
	 */
	public int getLocalPort() {
		return localPort;
	}

	/**
	 * @param localPort the localPort to set
	 */
	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	/**
	 * @return the remotePort
	 */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * @param remotePort the remotePort to set
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @param threadName the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}


}
