import java.util.LinkedList;

public class OpenMem 
{

	public OpenMem ()
	{}
	
	public boolean findOpenMem	(
			PCB PCB_Ready
			,LinkedList<PCB> QMemOpen
			,LinkedList<PCB> QMemUsed	)
	{
		boolean memFound__B = false ;
			
		int memNeed = PCB_Ready.get_Limit() ;	//@0100
		
		System.out.printf("\t==>@0100 OpenMem\tlooking for %d memory\n"	
				,memNeed	
				);
		
		for (int ii = 0; ii < QMemOpen.size(); ii ++)
		{
			PCB memOpen = QMemOpen.get(ii) ;	//@0120
			if (memOpen.get_Limit() > memNeed )	//@0200
			{
				memFound__B = true;
				int base0 = memOpen.get_memBase() ;
				//@200	found memory
				System.out.printf("\t@0200 Found:%d\tin location:%d\n"	
						,memOpen.get_Limit()
						,base0
						);				
				
				//@0220	split the open memory	@@ QMemOpen @@
				memOpen.set_memBase(base0 + memNeed);
				memOpen.set_memLimit(memOpen.get_Limit()-memNeed);
				
				//@0240	replace the open memory
				QMemOpen.set(ii ,memOpen);
				System.out.printf("\t@0240 Open:%d\t%s\n"	
						,QMemOpen.size()	
						,memOpen.showPCB()
						);
					
				//@0260	allocate the used memory	@@ QMemUsed @@
				PCB memUsed = new PCB(memNeed) ;
					
				//@0280	set the base for the process
				memUsed.set_memBase(base0);
					
				//@0290 set the PCB ID
				memUsed.set_PCB_ID(PCB_Ready.get_ID());				
				
				//@0300	push the used memory
				QMemUsed.addLast(memUsed);
					
				System.out.printf("\t@0300 Used\t%s\n"	,memUsed.showPCB());
					
				break ;	// exit out of the FOR loop for memory
			}
		}
		return memFound__B ;
	}
}
