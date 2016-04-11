import java.util.Collections;
import java.util.LinkedList;

public class DefragMem 
{
	public DefragMem ()
	{}
	
	public void defragMem	(
			 LinkedList<PCB> QMemOpen
			,LinkedList<PCB> QMemUsed	)
	{
		//#0500 Sort the QMemOpen 
		
		Collections.sort(QMemOpen);
		for (PCB loopI : QMemOpen)
		{
			System.out.printf("@df050 Sorted\t\tmemBase: %d\tmemLimit: %d\n"	
					,loopI.get_memBase()
					,loopI.get_Limit()
					);
		}
		
		//#0600 Defragmentation processing
		
		for (int ii = 0; ii < QMemOpen.size()-1; ii++)	
		{
			PCB pcb0 = QMemOpen.get(ii);
			PCB pcb1 = QMemOpen.get(ii+1);
		
			int nextMem = pcb0.get_memBase() + pcb0.get_Limit();
			if (nextMem == pcb1.get_memBase())
			{
				// adjacent memory
				System.out.printf("@df070\tMerging adjacent open memory: %d\t%d\n"
						,pcb0.get_memBase()
						,pcb1.get_memBase()
						);
				pcb0.set_memLimit(pcb0.get_Limit()+pcb1.get_Limit());
				QMemOpen.remove(ii+1);
				
				for (PCB loopI : QMemOpen)
				{
					System.out.printf("@df090 Open mem\t%s\n", loopI.showPCB()) ;
				}
			}
		}	
	}
}
