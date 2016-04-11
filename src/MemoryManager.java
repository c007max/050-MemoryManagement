import java.util.LinkedList ;

public class MemoryManager 
{			
	public static void main(String args[]) 	
	{
		int QREADY__T = 15 ;	final int mem__T = 256;
		
		LinkedList<PCB> QReady	= new LinkedList<PCB>();	//#0010 Create List QReady
		LinkedList<PCB> QMemOpen	= new LinkedList<PCB>();	//#0020 Create List QMemOpen
		LinkedList<PCB> QMemUsed	= new LinkedList<PCB>();	//#0030 Create List QMemUsed
		
		QMemOpen.add(new PCB(mem__T)) ;	//#0032 Set the Open Memory
						
		PCB PCB_Ready	; 	//#0040 Create the field: PCB_Ready

		for (int ii = 0; ii < QREADY__T; ii++)
		{
			PCB pcb0 = new PCB();		//#0050 new PCB STATE:New
			pcb0.set_state("Ready");	//#0060 set PCB STATE:Ready
			QReady.add(pcb0) ;			//#0070 Add PCB object onto QReady 
		}
		
		for (PCB loopI : QReady)
			System.out.printf("%s\n"	,loopI.showPCB()) ;
		
		//	End of Initialization \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
   	
		//#0100	create objects for OpenMem, FreeMem, and DefragMem	\\\\\\\\\\\\
		
		OpenMem om	= new OpenMem();
		FreeMem fm	= new FreeMem();
		DefragMem dm	= new DefragMem();
			
		//#0130	iterate through QReady
		while (QReady.size() > 0)		
		{
			PCB_Ready = QReady.removeFirst() ;
			
			System.out.printf("@0140 ID: %d needs: %d\n"	
					,PCB_Ready.get_ID()
					,PCB_Ready.get_Limit());
			
			//#0150	Looking for open memory
			boolean memFound__B	= om.findOpenMem(PCB_Ready, QMemOpen, QMemUsed);
			
			for (PCB loopI : QMemOpen)
				System.out.printf("@0150 Open\t\tmemBase: %d\tmemLimit: %d\n"	
						,loopI.get_memBase()
						,loopI.get_Limit()
						);
			
			for (PCB loopI : QMemUsed)
				System.out.printf("@0150 Used\t%s\n"	,loopI.showPCB());			
								
			if (!memFound__B)
			{
				System.out.printf("\n@0160 ### No Memory Open for Process:%d\tneeding:%d\n"
						,PCB_Ready.get_ID() 
						,PCB_Ready.get_Limit()	);
				
				//#0400	Free 1 random memory slot
				fm.free1Mem	(PCB_Ready
						,QMemOpen
						,QMemUsed
						);
											
				//#0500 Defragment Open Memory
				dm.defragMem (QMemOpen
						,QMemUsed
						);
				
				//#0600 reposition current to last in QReady
				QReady.addLast(PCB_Ready);
			}
			System.out.println("----------------------------------------------------");
		}
	}
}