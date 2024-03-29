package com.bbm.util.sys.pxy.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ProxyCommand {

	Socket ClientSocket;
    DataInputStream disReader;
    DataOutputStream dosWriter;

    String strReceive = null;
    String strLog = null;

    private String proxyIp;
    private int  proxyPort;

	public ProxyCommand(String proxyIp, int proxyPort) {
		setProxyIp(proxyIp);
		setProxyPort(proxyPort);
	}

	public void runCommand(String msg)
	{
		try{
        	ClientSocket = new Socket(proxyIp, proxyPort);

        	disReader = new DataInputStream(ClientSocket.getInputStream());
        	dosWriter = new DataOutputStream(ClientSocket.getOutputStream());

        	dosWriter.writeUTF(msg);
            dosWriter.flush();
        }
        catch(Exception e)
        {
        	//e.printStackTrace();
    	    throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
        } finally {
		    CloseSocket();
		}
	}

    private void CloseSocket()
    {
    	try
		{
	    	if(disReader !=null)
	    	{
	    		disReader.close();
	    		disReader = null;
	    	}
	    	if(dosWriter !=null)
	    	{
	    		dosWriter.close();
	    		dosWriter = null;
	    	}
	    	if(ClientSocket !=null)
	    	{
	    		ClientSocket.close();
	    		ClientSocket = null;
	    	}
		}
		catch(Exception e)
		{
        	//e.printStackTrace();
    	    throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
		}
    }

	/**
	 * @return the proxyIp
	 */
	public String getProxyIp() {
		return proxyIp;
	}

	/**
	 * @param proxyIp the proxyIp to set
	 */
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	/**
	 * @return the proxyPort
	 */
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param proxyPort the proxyPort to set
	 */
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
}

