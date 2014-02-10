package slm;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import shapes.ComponentModel;
import shapes.LabelModel;
import shapes.RemoteShape;
import shapes.StringModel;
import util.annotations.IsCompositeShape;
import util.annotations.Visible;
import util.models.AListenable;
import util.trace.Tracer;
//import util.Observable;
@util.annotations.StructurePattern(util.annotations.StructurePatternNames.HASHTABLE_PATTERN)
@IsCompositeShape(true)
public class SLModel extends AListenable implements ShapesList, java.io.Serializable, Cloneable
    {
    protected  Hashtable shapesTable = new Hashtable();

		public SLModel () throws RemoteException {
			
		}
		Hashtable labels = new Hashtable();
		boolean locked = false;
     	public void clear()
     	{
     	    shapesTable.clear();
     	    //this.setChanged();
     	    //this.notifyObservers();
     	    this.notifyListeners(new SLClearCommand(this));
     	}
		public static String getKey (Hashtable table, Object element) {
			for (Enumeration elements = table.keys(); elements.hasMoreElements();){
				String key = (String) elements.nextElement();
				if (table.get(key) == element)
					return key;
			}
			return null;	
				
		}
		public String getKey(String label) {
			return getKey(labels, label);
		}
		public String getKey(RemoteShape shape) {
			return getKey(shapesTable, shape);
		}
		//public void setController(slgc.SLGController c) {
		public void setController(Object c) {
			//System.out.println("setController called " + c);
		}
     	public RemoteShape get(String key)
     	{
	        return ((RemoteShape) shapesTable.get(key));
	    }
		public String getLabel(String key)
     	{
	        return ((String) labels.get(key));
	    }
		
	    public Enumeration keys()
	    {
	        return (shapesTable.keys());
	    }
	    public Enumeration elements()
	    {
	        //return (shapesTable.elements());
	    	return sortedList.elements();
	    }
//	    public List getSortedElements() {
//	    	return sortedList;
//	    }
		public Enumeration labels()
	    {
	        return (labels.elements());
	    }
		public boolean contains(Object o) {
			return shapesTable.contains(o);
		}
		public boolean containsKey(Object o) {
			return shapesTable.containsKey(o);
		}
	    //public ShapeModel put(String key, ShapeModel value)
		public synchronized RemoteShape put(String key, RemoteShape value)
	    {
//			if (sortedList.contains(value)) {
//				Message.warning("Shape displayed multiple times" + value + " " +  value.getClass());
//				return value;
//			}
			// added this recently, hope it does not expose some other bug
			remove(key);
			
//			if (value instanceof StringModel) {
//				System.out.println("Putting shape:" + value);
//			}
				
	        RemoteShape retVal = (RemoteShape) shapesTable.put(key,value);
//	        if (value instanceof StringModel) {
//	        	if (((StringModel) value).getText().equals("goodye"))
//	        	Tracer.info(this, "Adding strig model:" + value);
//	        	
//	        }
	        if (sortedList.contains(value)) {
	        	Tracer.info("Adding " + value +"again!");
	        	return retVal;
	        }
	        sortedList.add(value);
	        sort();
//	        if (value instanceof RemoteText) {
//	        	System.out.println("Remote Text");	        
//	        	
//	        }
	        //this.setChanged();
     	    //this.notifyObservers(value);
     	    //this.notifyListeners(new SLChange(key,value));
			this.notifyListeners(new SLPutCommand(this, key, value));
	        return (retVal);
	    }
		public synchronized String put(String key, String label)
	    {
			
	        String retVal = (String) labels.put(key,label);
	        //this.setChanged();
     	    //this.notifyObservers(value);
     	    //this.notifyListeners(new SLChange(key,value));
			this.notifyListeners(new SLPutLabelCommand(this, key, label));
	        return (retVal);
	    }
		public void setBounds(String k, Rectangle r) {
			try {
			RemoteShape s = get(k);
			if (s == null) return;
			s.setBounds(r.x, r.y, r.width, r.height);
			this.notifyListeners(new SLSetBoundsCommand(this, k, r));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    public RemoteShape remove(String key)
	    {
	        RemoteShape retVal = (RemoteShape) shapesTable.remove(key);
	        if (retVal == null)
	        	return null;
	        // this was all debug stuff
//	        if (retVal instanceof StringModel ) {
//	        	Tracer.info("Removing shape: " + retVal);
//	        } 
//	        else // why this else?
//	        System.out.println("removing:" + retVal);
	        sortedList.remove(retVal);
	        sortedComponentList.remove(retVal);
//	    	sortedList.remove(get(key));
//	        RemoteShape retVal = (RemoteShape) shapesTable.remove(key);
//	        if (retVal instanceof RemoteText)
//	        	System.out.println("Removing text box");
//	        
	        //this.setChanged();
	        //this.notifyObservers();
	        this.notifyListeners(new SLRemoveCommand(this, key, retVal));
	        return(retVal);
	    }
		 public String removeLabel(String key)
	    {
	        String retVal = (String) labels.remove(key);
	        //this.setChanged();
	        //this.notifyObservers();
	        this.notifyListeners(new SLRemoveLabelCommand(this, key));
	        return(retVal);
	    }

	    public void set(ShapesList newShapesList)
	    {
	        //this.shapesTable = newShapesList.shapesTable;
	        /*
	        System.out.println("Set Called");
	        */
	        shapesTable.clear();
	        for (Enumeration keys = newShapesList.keys();
                keys.hasMoreElements();)
            {
                String key = (String) keys.nextElement();
                this.put (key, newShapesList.get(key));
            }
           //this.notifyObservers();
           this.notifyListeners(new SLSetCommand(this, newShapesList));
        }
	   Vector sortedList = new Vector();
	   List sortedComponentList = new Vector();
	    public void sort() {
	    	Collections.sort(sortedList);
//	    	System.out.println("Sort:" + sortedList);
	    	sortedComponentList.clear();
	    	for (int i = 0; i < sortedList.size(); i++) {
	    		if (sortedList.get(i) instanceof ComponentModel)
	    			sortedComponentList.add(sortedList.get(i));
	    	}
	    }
	   
	    @Visible(false)
	   public  List getSortedList () {
		   return sortedList;
	   }
	    @Visible(false)

	   public  List getSortedComponentList () {
		   return sortedComponentList;
	   }

    public Object clone()
    {
        SLModel clone = null;
        try
        {
            clone = (SLModel) super.clone();
            clone.shapesTable = (Hashtable) this.shapesTable.clone();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return (clone);
        }
    }
     private void writeObject(java.io.ObjectOutputStream out)
        throws java.io.IOException
    {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in)
        throws java.io.IOException, ClassNotFoundException
    {
        in.defaultReadObject();
    }
    
    public String toString() {
    	return "Shape List Model: " + sortedList;
    }
	@Override
	public boolean locked() {
		return  locked;
	}
	@Override
	public void setLocked(boolean newVal) {
		if (locked == newVal)
			return;
		locked = newVal;
		if (!locked)
			notifyListeners();
		
	}


        
}