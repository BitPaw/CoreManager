package de.SSC.DigitalClock;

public class DigitalNumber 
{
	public final boolean[][] _doublePoint;
	
	private final boolean[][] _zero;
	private final boolean[][] _one;
	private final boolean[][] _two;
	private final boolean[][] _three;
	private final boolean[][] _four;
	private final boolean[][] _five;
	private final boolean[][] _six;
	private final boolean[][] _seven;
	private final boolean[][] _eight;
	private final boolean[][] _nine;	
	
	public final int MaxHeight;
	public final int MaxWidth;

	
	public DigitalNumber()
	{		
		MaxHeight = 5;
		MaxWidth = 3;		
		
		boolean[][] doublePoint = 
			{
				      {false, false, false}, 
				      {false, true, false}, 
				      {false, false, false}, 
				      {false, true, false}, 
				      {false, false, false}, 
			};	
		
		boolean[][] zero = 
			{
			      {true, true, true}, 
			      {true, false, true}, 
			      {true, false, true}, 
			      {true, false, true}, 
			      {true, true, true}, 
			};
		
		boolean[][] one = 
			{
			      {false, true, false}, 
			      {true, true, false}, 
			      {false, true, false}, 
			      {false, true, false}, 
			      {false, true, false}, 
			};
		
		boolean[][] two = 
			{
			      {true, true, true}, 
			      {false, false, true}, 
			      {true, true, true}, 
			      {true, false, false}, 
			      {true, true, true}, 
			};
		
		boolean[][] three = 
			{
				      {true, true, true}, 
				      {false, false, true}, 
				      {true, true, true}, 
				      {false, false, true}, 
				      {true, true, true}, 
			};
		
		boolean[][] four = 
			{
				      {true, false, true}, 
				      {true, false, true}, 
				      {true, true, true}, 
				      {false, false, true}, 
				      {false, false, true}, 
			};
		
		boolean[][] five = 
			{
				      {true, true, true}, 
				      {true, false, false}, 
				      {true, true, true}, 
				      {false, false, true}, 
				      {true, true, true}, 
			};
		
		boolean[][] six = 
			{
			      {true, true, true}, 
			      {true, false, false}, 
			      {true, true, true}, 
			      {true, false, true}, 
			      {true, true, true}, 
			};
		
		boolean[][] seven = 
			{
			      {true, true, true}, 
			      {false, false, true}, 
			      {false, false, true}, 
			      {false, false, true}, 
			      {false, false, true}, 
			};
		
		boolean[][] eight = 
			{
			      {true, true, true}, 
			      {true, false, true}, 
			      {true, true, true}, 
			      {true, false, true}, 
			      {true, true, true}, 
			};
		
		boolean[][] nine = 
			{
			      {true, true, true}, 
			      {true, false, true}, 
			      {true, true, true}, 
			      {false, false, true}, 
			      {false, false, true}, 
			};		
		
		// Set values
		_doublePoint = doublePoint;
		
		_zero = zero;
		_one = one;
		_two = two;
		_three =three;
		_four = four;
		_five = five;
		_six = six;
		_seven = seven;
		_eight = eight;
		_nine = nine;	

	}
			
	public boolean[][] GetNumberLayout(int wantedNumber)
	{
		boolean[][] number;
		
		switch(wantedNumber)
		{
		case 0:
			number = _zero;
			break;
			
		case 1:
			number = _one;
			break;
			
		case 2:
			number = _two;
			break;
			
		case 3:
			number = _three;
			break;
			
		case 4:
			number = _four;
			break;
			
		case 5:
			number = _five;
			break;
			
		case 6:
			number = _six;
			break;
			
		case 7:
			number = _seven;
			break;
			
		case 8:
			number = _eight;
			break;
			
		case 9:
			number = _nine;
			break;
			
			default:
				//throw new Exception("Illigal time number."); 	
				number = _zero;
				break;
			
		}
		
		
		return number;
	}
}
