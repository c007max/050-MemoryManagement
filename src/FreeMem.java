import java.util.LinkedList;
import java.util.Random ;

public class FreeMem 
{
	Random random__X	= new Random();

	public FreeMem ()
	{}
	
	public void free1Mem	(
			PCB PCB_Ready
			,LinkedList<PCB> QMemOpen
			,LinkedList<PCB> QMemUsed	)
	{
		//#0400	simulate memory free process
		
		if (!QMemUsed.isEmpty())	//	check that memory is in use, i.e. NOT empty
		{
			int QIndex	= random__X.nextInt(QMemUsed.size()) ;
			
			//	free the memory 
			PCB PCB_Done = QMemUsed.remove(QIndex);
			
			System.out.printf("\n@fm040 ***** Removing: %d\t******\n"
					,PCB_Done.get_ID()
					);

			//	add the memory back to the QMemOpen
			QMemOpen.add(PCB_Done);		
			
			for (PCB loopI : QMemUsed)
				System.out.printf("@fm060 Used\t%s\n"	,loopI.showPCB());	
			
			for (PCB loopI : QMemOpen)
				System.out.printf("@fm080 Open\t\tmemBase: %d\tmemLimit: %d\n"	
						,loopI.get_memBase()
						,loopI.get_Limit()
						);
		}
	}
}
