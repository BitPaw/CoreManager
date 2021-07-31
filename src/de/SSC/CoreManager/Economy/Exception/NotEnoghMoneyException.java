package de.SSC.CoreManager.Economy.Exception;

public class NotEnoghMoneyException extends Exception 
{
	private static final long serialVersionUID = 8853767015451446831L;
	public final float CurrentBalance;
	public final float WantedAmount;
	public final float MoneyDifference;
	
	public NotEnoghMoneyException(float currentBalance, float wantedAmount) 
    {
        super();
        
        CurrentBalance = currentBalance;
        WantedAmount = wantedAmount;
        MoneyDifference = Math.abs(currentBalance - wantedAmount);
    }
}