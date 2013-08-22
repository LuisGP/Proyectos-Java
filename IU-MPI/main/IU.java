package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Arc2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class IU  implements ActionListener{

	private ClusterInfo cluster;
	
	private JFrame jFrame = null;
	private JPanel jPanel = null;
	private JPanel resumen = null;
	private JPanel info = null;
	private JPanel cpu = null;
	private JPanel memoria = null;
	//private JPanel io = null;
	private JScrollPane jScrollPane = null;
	private JTabbedPane jTabbedPane = null;

	private int nodoSeleccionado = 1;
	
	private String[] nodos;
	private JList jList = null;

	// Graficas
	private CpuGraphic cpuGraphic;
	private ProcGraphic procGraphic;
	private MemGraphic memGraphic;
	private SwapGraphic swapGraphic;
	
	private int totalmem, totalmemusage;
	private float totalcpuUsage;
	private CpuGraphic totalCpuGr;
	private MemGraphic totalMemGr;
	
	// Resumen
	private JLabel numNodos = new JLabel("Numero Nodos: ");
	private JLabel cpusTotales = new JLabel("Numero CPUs: ");
	private JLabel memoriaTotal = new JLabel("Memoria Total: ");
	private JLabel usoCPU = new JLabel("Uso de CPU: ");
	private JLabel usoMemoria = new JLabel("Uso de Memoria: ");
	private JButton cerrar = new JButton("Cerrar");
	
	// Info
	private JLabel trefresco_label = new JLabel("Tiempo de Refresco: ");
	private JTextField trefresco = new JTextField(10);
	private JButton nuevoTRefresco = new JButton("Actualizar");
	private int tiempo_refresco = 5;
	private JLabel procesador = new JLabel("Procesador: ");
	private JLabel memoriaNodo = new JLabel("Memoria Total: ");
	
	// CPU
	private JLabel cpuspeed = new JLabel("Velocidad: ");
	private JLabel numcpus = new JLabel("Numero de CPUs: ");
	private JLabel procactivos = new JLabel("Procesos activos: ");
	private JLabel procblk = new JLabel("Procesos bloqueados: ");
	private JLabel proctotales = new JLabel("Procesos totales: ");
	private JLabel cpuusage = new JLabel("Uso de CPU: ");	
	
	// Memoria
	private JLabel memtotal = new JLabel("Memoria Total: ");
	private JLabel memfree = new JLabel("Memoria Libre: ");
	private JLabel swaptotal = new JLabel("Swap Total: ");
	private JLabel swapfree = new JLabel("Swap Libre: ");
	
	// I/O
	
	// Comandos
	public static final int NODE_INFO = 1;
	public static final int CLOSE = 2;
	public static final int TREFRESCO = 3;
	public static final int ERROR = 4;
	public static final int NUMNODOS = 5;
	public static final int UPDATE = 6;

	public int getNodoSeleccionado(){
		return nodoSeleccionado;
	}
	
	
	public IU(){
		cluster = new ClusterInfo(this);

		this.nodos = new String[cluster.getNNodes()];
		for(int i = 0; i < cluster.getNNodes(); i++){
			this.nodos[i] = "N"+(i+1);
		}
		
		if(cluster.getNNodes() == 0)
			cluster.morir();

		// Inicializar las graficas
		cpuGraphic = new CpuGraphic(100,100);
		procGraphic = new ProcGraphic(100,100);
		memGraphic = new MemGraphic(100, 100);
		swapGraphic = new SwapGraphic(100, 100);
		
		totalCpuGr = new CpuGraphic(100,100);
		totalMemGr = new MemGraphic(100,100);

		
		jFrame = getJFrame();
		jFrame.setVisible(true);
	}
	
	public void setCluster(ClusterInfo ci){
		cluster = ci;
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setContentPane(getJPanel());
			jFrame.setTitle("Cluster");
			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jFrame.setSize(600,400);
			cerrar.addActionListener(this);

			jList.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e){
						nodoSeleccionado = jList.getSelectedIndex() + 1;
						updateInfo();
					}
				});
				
			jList.setSelectedIndex(0);
			jFrame.addWindowListener(
				new WindowListener(){
					public void windowClosing(WindowEvent e){
						System.out.println("Matando!!");
						cluster.morir();
					}
					public void windowActivated(WindowEvent e){}
					public void windowDeactivated(WindowEvent e){}
					public void windowClosed(WindowEvent e){}
					public void windowIconified(WindowEvent e){}
					public void windowDeiconified(WindowEvent e){}
					public void windowOpened(WindowEvent e){}
				});
			nuevoTRefresco.addActionListener(this);
			updateInfo();
		}
		return jFrame;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 0.7;
			gridBagConstraints.weighty = 0.7;
			gridBagConstraints.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJScrollPane(), gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 1;
			jPanel.add(getJTabbedPane(), gridBagConstraints);
		}
		return jPanel;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			//jScrollPane.setViewportView(getJTabbedPane());
			
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}
	private JList getJList() {
		if(jList == null){
			jList  = new JList(nodos);
		}
		return jList;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.add("Resumen Cluster", getResumen());
			jTabbedPane.add("Info", getInfo());
			jTabbedPane.add("CPU", getCPU());
			jTabbedPane.add("Memoria", getMemoria());
			//jTabbedPane.add("I/O", getIO());
		}
		return jTabbedPane;
	}
	
	private JPanel getResumen(){
		if(resumen == null){
			resumen = new JPanel();
			
			resumen.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;			
			resumen.add(numNodos, gridBagConstraints);
			try{
				System.out.println("NUMERO DE NODOS: ");
				int n = cluster.getNNodes();
				System.out.println(""+n);
				numNodos.setText("Numero Nodos: "+cluster.getNNodes());
			}catch(Exception e) {
				System.out.println("No nodos??");
			}
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;			
			resumen.add(this.cpusTotales, gridBagConstraints);
			try{
				int ncpus = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					ncpus += cluster.getNode(i).getNumcpus(); 
				}
				cpusTotales.setText("Numero CPUs: "+ncpus);
			}catch(Exception e) {}
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 2;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;			
			resumen.add(this.memoriaTotal, gridBagConstraints);
			try{
				int mem = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					String aux = cluster.getNode(i).getMemtotal();
					//System.out.println("Memoria: "+aux+" long: "+aux.length());
					//System.out.println("Memoria sin kB: "+aux.substring(0, aux.length()-4));
					int num = Integer.parseInt(aux.substring(0, aux.length() - 4));
					//System.out.println("NUMERITO: "+num);
					mem += num; 
				}
				memoriaTotal.setText("Memoria Total: "+mem+"kB");
				totalmem = mem;
			}catch(Exception e) {}
			
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 3;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;			
			resumen.add(this.usoCPU, gridBagConstraints);
			try{
				float usocpu = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					usocpu += cluster.getNode(i).getCpuusage(); 
				}
				usoCPU.setText("Uso de CPUs: "+usocpu);
				totalcpuUsage = usocpu;
			}catch(Exception e) {}
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 4;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;			
			resumen.add(this.usoMemoria, gridBagConstraints);
			try{
				int mem = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					String aux = cluster.getNode(i).getMemtotal();
					String aux2 = cluster.getNode(i).getMemfree(); 
					aux = aux.substring(0, aux.length() - 4);
					aux2 = aux2.substring(0, aux2.length() - 4);
					int total = Integer.parseInt(aux);
					int free = Integer.parseInt(aux2);
					
					mem += total - free;
				}
				usoMemoria.setText("Uso de Memoria: "+mem+"kB");
				totalmemusage = mem;
			}catch(Exception e) {}
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 5;
			gridBagConstraints.weightx = 0.5;
			gridBagConstraints.weighty = 0.5;
			gridBagConstraints.gridx = 0;
			resumen.add(cerrar, gridBagConstraints);
		}
		return resumen;
	}
	
	private JPanel getInfo(){
		if(info == null){
			info = new JPanel();
			
			info.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			/*gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = .5;
			gridBagConstraints.weighty = .5;
			gridBagConstraints.gridx = 0;
			info.add(this.procesador, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = .50;
			gridBagConstraints.weighty = .50;
			gridBagConstraints.gridx = 0;
			info.add(this.memoriaNodo, gridBagConstraints);*/
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			
			/*JPanel panel2 = new JPanel();
			info.add(panel2, gridBagConstraints);*/
			
			info.setLayout(new GridBagLayout());
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			info.add(this.trefresco_label, gridBagConstraints);
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 1;
			info.add(this.trefresco, gridBagConstraints);
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 2;
			info.add(this.nuevoTRefresco, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			info.add(this.totalCpuGr, gridBagConstraints);
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 2;
			info.add(this.totalMemGr, gridBagConstraints);
		}
		return info;
	}
	
	private JPanel getCPU(){
		if(cpu == null){
			cpu = new JPanel();
			
			cpu.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;
			cpu.add(this.numcpus, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;
			cpu.add(this.procesador, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 2;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			cpu.add(this.cpuspeed, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 3;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;			
			cpu.add(this.proctotales, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 4;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;			
			cpu.add(this.procactivos, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 5;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;			
			cpu.add(this.procblk, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 6;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;			
			cpu.add(this.cpuusage, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 7;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			JPanel panel2 = new JPanel();
			cpu.add(panel2, gridBagConstraints);

			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			panel2.add(this.cpuGraphic, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 1;			
			panel2.add(this.procGraphic, gridBagConstraints);
		}
		return cpu;
	}
	
	private JPanel getMemoria(){
		if(memoria == null){
			memoria = new JPanel();
			
			memoria.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 0;
			memoria.add(this.memtotal, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			gridBagConstraints.gridx = 1;
			memoria.add(this.memfree, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			memoria.add(this.swaptotal, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 1;			
			memoria.add(this.swapfree, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 2;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			memoria.add(memGraphic, gridBagConstraints);
			
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 2;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 1;			
			memoria.add(swapGraphic, gridBagConstraints);
		}
		return memoria;
	}
	
	/*private JPanel getIO(){
		if(io == null){
			io = new JPanel();
			
			io.add(new JLabel("Not implemented!! :P"));
		}
		return io;
	}*/
	
	public static void main(String args[]){
		new IU();
	}

	
	
	
	// EVENTOS	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == this.cerrar){
			//System.out.println("CERRAR MONITOR");
			cluster.morir();
		}else if(source == this.nuevoTRefresco){
			int t;
			try{
				t = Integer.parseInt(trefresco.getText());
				cluster.Send(IU.TREFRESCO, t);
			}catch(Exception exc) {
				System.out.println("Debe introducir un numero entero");
			}
		}
	}

	public void updateInfo(){
		NodeInfo ni;
		
		if(cluster.getNNodes() > 0){
			ni = cluster.getNode(nodoSeleccionado - 1);

			procesador.setText("Procesador: "+ni.getCpuType());
			memoriaNodo.setText("Memoria Total: "+ni.getMemtotal());
			cpuspeed.setText("Velocidad: "+ni.getCpuspeed());
			numcpus.setText("Numero de CPUs: "+ni.getNumcpus());
			procactivos.setText("Procesos activos: "+ni.getProcactivos());
			procblk.setText("Procesos bloqueados: "+ni.getProcblk());
			proctotales.setText("Procesos totales: "+ni.getProctotales());
			cpuusage.setText("Uso de CPU: "+ni.getCpuusage());
			memtotal.setText("Memoria Total: "+ni.getMemtotal());
			memfree.setText("Memoria Libre: "+ni.getMemfree());
			swaptotal.setText("Swap Total: "+ni.getSwaptotal());
			swapfree.setText("Swap Libre: "+ni.getSwapfree());
			
			// Actualiza las tablas
			procGraphic.setDatos(ni.getProctotales(), ni.getProcactivos(), ni.getProcblk());
			procGraphic.repaint();
			
			cpuGraphic.setDatos(ni.getCpuusage());
			cpuGraphic.repaint();
			
			int t, u, f;
			try{
				t = Integer.parseInt(ni.getMemtotal().substring(0, ni.getMemtotal().length()-4));
				f = Integer.parseInt(ni.getMemfree().substring(0, ni.getMemfree().length()-4));
				u = t - f;
				memGraphic.setDatos(t, u);
			}catch(Exception e){}
			memGraphic.repaint();
			
			try{
				t = Integer.parseInt(ni.getSwaptotal().substring(0, ni.getSwaptotal().length()-4));
				f = Integer.parseInt(ni.getSwapfree().substring(0, ni.getSwapfree().length()-4));
				u = t - f;
				//System.out.println("TOTAL: "+t+" - USO: "+u);
				swapGraphic.setDatos(t, u);
			}catch(Exception e){}
			swapGraphic.repaint();
			
			// Las gráficas y datos totales
			try{
				numNodos.setText("Numero Nodos: "+cluster.getNNodes());
			}catch(Exception e) {}
			try{
				int ncpus = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					ncpus += cluster.getNode(i).getNumcpus(); 
				}
				cpusTotales.setText("Numero CPUs: "+ncpus);
			}catch(Exception e) {}
			try{
				int mem = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					String aux = cluster.getNode(i).getMemtotal();
					int num = Integer.parseInt(aux.substring(0, aux.length() - 4));
					mem += num; 
				}
				memoriaTotal.setText("Memoria Total: "+mem+"kB");
				totalmem = mem;
			}catch(Exception e) {}
			try{
				float usocpu = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					usocpu += cluster.getNode(i).getCpuusage(); 
				}
				usoCPU.setText("Uso de CPUs: "+usocpu);
				totalcpuUsage = usocpu;
			}catch(Exception e) {}
			try{
				int mem = 0;
				for(int i = 0; i < cluster.getNNodes(); i++){
					String aux = cluster.getNode(i).getMemtotal();
					String aux2 = cluster.getNode(i).getMemfree(); 
					aux = aux.substring(0, aux.length() - 4);
					aux2 = aux2.substring(0, aux2.length() - 4);
					int total = Integer.parseInt(aux);
					int free = Integer.parseInt(aux2);
					
					mem += total - free;
				}
				usoMemoria.setText("Uso de Memoria: "+mem+"kB");
				totalmemusage = mem;
			}catch(Exception e) {}
			
			totalCpuGr.setDatos(totalcpuUsage);
			totalMemGr.setDatos(totalmem, totalmemusage);
			
		}
	}
}


class ClusterInfo extends Thread{
	private int nNodes;
	private NodeInfo[] nodes;
	private IU iu;
	private int tiempo_refresco = 5;

	private boolean vivo = true;
	
	public ClusterInfo(IU iu){
		this.Send(IU.NUMNODOS, null);
		nodes = new NodeInfo[nNodes];
		this.iu = iu;
		this.iu.setCluster(this);

		//Primera encuesta
		for(int i = 0; i < nNodes; i++)
			this.Send(IU.NODE_INFO, new Integer(i+1));
		
		this.start();
	}
	
	public int getTRefresco(){
		return tiempo_refresco;
	}
	
	public void setTRefresco(int t){
		tiempo_refresco = t;
	}
	
	public void setNode(NodeInfo node, int i){
		nodes[i] = node;
	}
	
	public NodeInfo getNode(int i){
		return nodes[i];
	}
	
	public int getNNodes(){
		return nNodes;
	}
	
	public void run(){
		long time = System.currentTimeMillis();

		while(vivo){
			if((time + tiempo_refresco*1000) <= System.currentTimeMillis()){
				this.Send(IU.UPDATE, null);
				// El nodo 0 es el MASTER			
				for(int i = 0; i < nNodes; i++)
					this.Send(IU.NODE_INFO, new Integer(i+1));
				time = System.currentTimeMillis();
				iu.updateInfo();
			}
			try{
				Thread.sleep(tiempo_refresco*1000);
			}catch(Exception e){}
		}
	}

	public void morir(){
		try{
			this.Send(IU.CLOSE, null);
			this.vivo = false;
			this.finalize();
		}catch(Throwable e) {}
	}

	// Conexion
	public void Send(int comando, Object dato){
		try {
    	 	Socket skt = new Socket("localhost",1234);
    	 	DataInputStream in = new DataInputStream(skt.getInputStream());
    	 	DataOutputStream os = new DataOutputStream(skt.getOutputStream());
     		
    	 	os.writeInt(comando);
    	 	
    	 	switch(comando){
    	 		case IU.NODE_INFO:
    	 			// Que nodo
    	 			os.writeInt(((Integer)dato).intValue());
    	 			// Recibir
    	 			if(in.readInt() == IU.NODE_INFO){    	 				
    	 				byte[] info = new byte[1024];
    	 				in.read(info);
    	 				
    	 				NodeInfo ni = new NodeInfo(info);
    	 				this.setNode(ni, ((Integer)dato).intValue()-1);
    	 				// Lo mostramos:
    	 				//System.out.println("Recibido: \n"+ni.toString());
    	 			}else{
    	 				System.out.println("Error al recuperar informacion del nodo");
    	 			}

    	 			break;
    	 		case IU.CLOSE:
    	 			// Recibir
    	 			if(in.readInt() == IU.CLOSE){
    	 				System.out.println("Cerrando");
    	 			}else{
    	 				System.out.println("Error al cerrar");
    	 			}
				this.morir();    	 				
    	 			break;
    	 		case IU.TREFRESCO:
    	 			// Enviamos el nuevo tiempo
				//System.out.println("Nuevo refresco: "+((Integer)dato).intValue());
    	 			os.writeInt(((Integer)dato).intValue());
				os.writeInt(iu.getNodoSeleccionado());
    	 			// Recibir
    	 			if(in.readInt() == IU.TREFRESCO){
    	 				//System.out.println("Ok, nuevo refresco");
    	 				this.setTRefresco(((Integer)dato).intValue());
    	 				//this.tiempo_refresco = ((Integer)dato).intValue();
    	 			}else{
    	 				System.out.println("Error en tiempo refresco");
    	 			}
    	 			break;
    	 		case IU.ERROR:
    	 			// No puede darse, error nos lo mandan
    	 			break;
    	 		case IU.NUMNODOS:
    	 				int nnodos = in.readInt();
					System.out.println("Numero de nodos: " + (nnodos-1));
    	 				nNodes = nnodos-1;
    	 			break;
			case IU.UPDATE:
				// Nada, el se actualiza y listo ^^
				break;
			default:
				System.out.println("No existe tal comando");
				break;
    	 		
    	 	}

	        skt.close();
  		}catch(Exception e) {
    	 		System.out.print("Whoops! It didn't work!\n");
			//e.printStackTrace();
		}
	}
}

class NodeInfo{
	private final int clength = 64;
		
	private int id;
	private char[] cpuType = new char[clength];
	private float cpuspeed;
	private int numcpus;
	
	private char[] memtotal = new char[clength];
	private char[] memfree = new char[clength];
	private char[] swaptotal = new char[clength];
	private char[] swapfree = new char[clength];
	
	private int procactivos;
	private int procblk;
	private int proctotales;
	private float cpuusage;

	public static int byteAtoInt(byte[] b, int pos){
		int l = 0;
		l |= b[pos+3] & 0xFF;
		l <<= 8;
		l |= b[pos+2] & 0xFF;
		l <<= 8;
		l |= b[pos+1] & 0xFF;
		l <<= 8;
		l |= b[pos] & 0xFF;
		return l;
	}

	public static float byteAtoFloat(byte[] b, int pos){
		int l = 0;
		l |= b[pos+3] & 0xFF;
		l <<= 8;
		l |= b[pos+2] & 0xFF;
		l <<= 8;
		l |= b[pos+1] & 0xFF;
		l <<= 8;
		l |= b[pos] & 0xFF;

		float f = Float.intBitsToFloat(l);

		return f;
	}

	public static char[] byteAtoCharA(byte[] b, int pos, int length){
		char[] c = new char[length];
		char[] c2;
		int l = 0;

		for(int i = pos; i < pos+length; i++){
			c[i-pos] = (char)b[i];
			if(b[i] == 0)
				break;
			else
				l++;
		}
		
		c2 = new char[l];

		for(int i = 0; i < l; i++)
			c2[i] = c[i];

		return c2;
	}
	
	public NodeInfo(byte[] info){
		int offset = 0;

		id = NodeInfo.byteAtoInt(info,offset);
		offset += 4;

		cpuType = NodeInfo.byteAtoCharA(info,offset,clength);
		offset += clength;

		cpuspeed = NodeInfo.byteAtoFloat(info,offset);
		offset += 4;

		numcpus = NodeInfo.byteAtoInt(info,offset);
		offset += 4;

		memtotal = NodeInfo.byteAtoCharA(info,offset,clength);
		offset += clength;
		memfree = NodeInfo.byteAtoCharA(info,offset,clength);
		offset += clength;
		swaptotal = NodeInfo.byteAtoCharA(info,offset,clength);
		offset += clength;
		swapfree = NodeInfo.byteAtoCharA(info,offset,clength);
		offset += clength;

		procactivos = NodeInfo.byteAtoInt(info,offset);
		offset += 4;
		procblk = NodeInfo.byteAtoInt(info,offset);
		offset += 4;
		proctotales = NodeInfo.byteAtoInt(info,offset);
		offset += 4;
		cpuusage = NodeInfo.byteAtoFloat(info,offset);
		offset += 4;


		//System.out.println("Id:" + id + "\nCpu = "+cpuType);
		
	}
	
	public float getCpuspeed() {
		return cpuspeed;
	}
	public void setCpuspeed(float cpuspeed) {
		this.cpuspeed = cpuspeed;
	}
	public String getCpuType() {
		return new String(cpuType);
	}
	public void setCpuType(char[] cpuType) {
		this.cpuType = cpuType;
	}
	public float getCpuusage() {
		return cpuusage;
	}
	public void setCpuusage(float cpuusage) {
		this.cpuusage = cpuusage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemfree() {
		return new String(memfree);
	}
	public void setMemfree(char[] memfree) {
		this.memfree = memfree;
	}
	public String getMemtotal() {
		return new String(memtotal);
	}
	public void setMemtotal(char[] memtotal) {
		this.memtotal = memtotal;
	}
	public int getNumcpus() {
		return numcpus;
	}
	public void setNumcpus(int numcpus) {
		this.numcpus = numcpus;
	}
	public int getProcactivos() {
		return procactivos;
	}
	public void setProcactivos(int procactivos) {
		this.procactivos = procactivos;
	}
	public int getProcblk() {
		return procblk;
	}
	public void setProcblk(int procblk) {
		this.procblk = procblk;
	}
	public int getProctotales() {
		return proctotales;
	}
	public void setProctotales(int proctotales) {
		this.proctotales = proctotales;
	}
	public String getSwapfree() {
		return new String(swapfree);
	}
	public void setSwapfree(char[] swapfree) {
		this.swapfree = swapfree;
	}
	public String getSwaptotal() {
		return new String(swaptotal);
	}
	public void setSwaptotal(char[] swaptotal) {
		this.swaptotal = swaptotal;
	}
	
	public String toString(){
		String str = new String();
		
		str = "Id: "+id;
		str += "\nCputype: "+getCpuType();
		str += "CpuSpeed: "+cpuspeed;
		str += "\nNumCpus: "+numcpus;
		str += "\nMemTotal: "+getMemtotal();
		str += "MemFree: "+getMemfree();
		str += "SwapTotal: "+getSwaptotal();
		str += "SwapFree: "+getSwapfree();
		str += "ProcAct: "+procactivos;
		str += "\nProcBlk: "+procblk;
		str += "\nProcTotal: "+proctotales;
		str += "\nCpuUsage: "+cpuusage;
		
		return str;
	}
}

class CpuGraphic extends JPanel{

	double usocpu = 0;
	Lienzo lienzo = new Lienzo();
	
	public void setDatos(double cpu){
		usocpu = cpu;
	}
	
	public CpuGraphic(int w, int h){
		this.setSize(w, h);
		
		this.add(lienzo);
		
		pintar();
	}
	
	public void update(Graphics g){
		super.update(g);
		pintar();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		pintar();
	}
	
	private void pintar(){
		double pusado = 3.6 * usocpu; 
		Arc2D.Double uso = new Arc2D.Double(0,0,100,100,0,pusado,Arc2D.PIE);
		Arc2D.Double cien = new Arc2D.Double(0,0,100,100,0,360,Arc2D.PIE);
		
		Shape[] f = {cien, uso};
		Color[] c = {Color.BLUE, Color.RED};
		
		lienzo.tomaFiguras(f);
		lienzo.setColor(c);
		
		lienzo.repaint();
	}
}

class ProcGraphic extends JPanel{

	int act = 0;
	int blk = 0;
	int total = 1;
	
	Lienzo lienzo = new Lienzo();
	
	public void setDatos(int total, int act, int blk){
		this.act = act;
		if(total != 0)
			this.total = total;
		this.blk = blk;
	}
	
	public ProcGraphic(int w, int h){
		this.setSize(w, h);
		
		this.add(lienzo);
		
		this.pintar();
	}
	
	public void update(Graphics g){
		super.update(g);
		pintar();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		pintar();
	}

	private void pintar(){
		Rectangle total = new Rectangle(20,0,20,100);
		int a = this.act*100/this.total;
		Rectangle act = new Rectangle(40,100-a,20,a);
		int b = this.blk*100/this.total;
		Rectangle blk = new Rectangle(60,100-b,20,b);
		
		Shape[] f = {total, act, blk};
		Color[] c = {Color.BLUE, Color.GREEN, Color.RED};
		
		lienzo.tomaFiguras(f);
		lienzo.setColor(c);
		
		lienzo.repaint();
	}
}

class MemGraphic extends JPanel{

	int uso = 0;
	int total = 1;

	Lienzo lienzo = new Lienzo();
	
	public void setDatos(int tot, int uso){
		if(tot != 0)
			total = tot;
		this.uso = uso;
	}
	
	public MemGraphic(int w, int h){
		this.setSize(w, h);
		
		this.add(lienzo);
		
		pintar();
	}
	
	public void update(Graphics g){
		super.update(g);
		pintar();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		pintar();
	}

	private void pintar(){
		Rectangle total = new Rectangle(45,0,20,100);
		int u = this.uso*100/this.total;
		Rectangle act = new Rectangle(45,100-u,20,u);
		
		Shape[] f = {total, act};
		Color[] c = {Color.BLUE, Color.RED};
		
		lienzo.tomaFiguras(f);
		lienzo.setColor(c);
		
		lienzo.repaint();
	}
}

class SwapGraphic extends JPanel{
	int uso = 0;
	int total = 1;

	Lienzo lienzo = new Lienzo();
	
	public void setDatos(int tot, int uso){
		if(tot != 0)
			total = tot;
		this.uso = uso;
	}
	
	public SwapGraphic(int w, int h){
		this.setSize(w, h);
		
		this.add(lienzo);
		
		pintar();
	}
	
	public void update(Graphics g){
		super.update(g);
		pintar();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		pintar();
	}
	
	private void pintar(){
		Rectangle total = new Rectangle(45,0,20,100);
		int u = this.uso*100/this.total;
		Rectangle act = new Rectangle(45,100-u,20,u);
		
		Shape[] f = {total, act};
		Color[] c = {Color.BLUE, Color.RED};
		
		lienzo.tomaFiguras(f);
		lienzo.setColor(c);
		
		lienzo.repaint();
	}
}

class Lienzo extends Canvas
{
	Shape[] figuras;
	Color[] color;
	
    public Lienzo()
    {
        this.setSize (100, 100);
    }
    
    public void paint(Graphics g)
    {
    	Graphics2D g2 = (Graphics2D)g;
    	
    	if(figuras != null && color != null && figuras.length == color.length){
    		for(int i = 0; i < figuras.length; i++){
    			g2.setColor(this.color[i]);
    			g2.fill(figuras[i]);
    		}
    	}
    }
    
    public void setColor(Color[] color){
    	this.color = color;
    }

    public void tomaFiguras(Shape[] fig)
    {
        this.figuras = fig;
    }
}
