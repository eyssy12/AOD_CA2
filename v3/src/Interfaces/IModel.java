package Interfaces;

public interface IModel
{
    public void addObserver(IMyObserver observer);
    public void deleteObserver(IMyObserver observer);
    public void deleteObservers();
    public void notifyObservers();
}