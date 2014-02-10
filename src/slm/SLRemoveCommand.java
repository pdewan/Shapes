package slm;
import java.util.Enumeration;

import util.undo.AbstractCommand;
import util.undo.Undoer;
import shapes.RemoteShape;
import shapes.ShapeModel;
public class SLRemoveCommand extends util.undo.AbstractCommand
{
    private ShapesList slModel;
    private RemoteShape originalShapeModel = null;
    private RemoteShape shapeModelArg;
    private String keyArg;
    public SLRemoveCommand (ShapesList theShapesList,
       String theKey, RemoteShape theRemovedShapeModel)
    {
        slModel = theShapesList;
        keyArg = theKey;
        originalShapeModel = theRemovedShapeModel; // needed before execute is done
    }
	public String getKey() {
		return keyArg;
	}
	public RemoteShape getOriginalShapeModel() {
		return originalShapeModel;
	}
    public boolean isUndoable()
    {
        return(true);
    }
    public void execute()
    {
        originalShapeModel = slModel.remove(keyArg);
    }
    public void undo()
    {
        if (originalShapeModel != null)
            slModel.put(keyArg, originalShapeModel);
    }
    public void redo()
    {
        execute();
    }

    public static void printKeys(ShapesList theShapesList)
    {
        System.out.println("Printing Keys");
        for (Enumeration keys = theShapesList.keys();
                keys.hasMoreElements();)
            {
                String key = (String) keys.nextElement();
                System.out.println("Key" + (String) key);
            }
    }

    public static void remove (Undoer undoer, ShapesList theShapesList,
        String theKey)
    {
        SLRemoveCommand c =  new SLRemoveCommand(theShapesList, theKey, theShapesList.get(theKey));
        undoer.execute(c);
    }


}

