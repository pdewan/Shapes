package slc;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;

import util.misc.StreamTokenizer;
import util.undo.CommandHistory;
import util.undo.HistoryUndoer;
import util.undo.ToggleUndoer;
import util.undo.Undoer;
import shapes.LineModel;
import shapes.OvalModel;
//import shapes.SLComposer;
import slc.SLComposer;
import slm.SLModel;
import slm.ShapesList;
import slm.SLUndoProxy;
import slgv.SLGView;
import sltv.SLTView;
import sltv.SLTokens;
import slgc.SLGController;
import sltc.SLTController;

import java.awt.Dimension;
import java.awt.Color;

public class SLBusComposer extends SLComposer
{

//    static public void main (String args[]) throws Exception
//    {
//        Frame frame = new Frame();
//        SLComposer slComposer = new SLBusComposer(frame);
//        frame.add(slComposer.getContainer());
//        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
//        //frame.setLocation(numGViews*50, numGViews*50);
//        //frame.add(controlPanel, BorderLayout.WEST);
//        //add(slgView);
//        frame.setVisible(true);
//        System.out.println("Set main frame visible");
//    }
//
//
//    public SLBusComposer (Frame frame) throws Exception
//    {
//		
//    }
//	
//    {
//		
//    }
//	BusAgent busAgent;
//		busAgent.register(busAgent);
//		/*
//		System.out.println("composer = " + busAgent.register(this, "composer"));
//        System.out.println("undoer = " + busAgent.register(undoer , "history_undoer"));
//		System.out.println("model = " + register(slModel, "model"));
//		System.out.println("composer = " + register(this, "composer"));
//        System.out.println("undoer = " + busAgent.register(undoer , "history_undoer"));
//		busAgent.register (slgView); 
//		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", "Controller","Model", "Controller", ShapesList.class);
//		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", null,"Model", null, ShapesList.class);
//		busAgent.registerProxy(slModelUndoProxy,Object.class, "Model", null,"Model", null, ShapesList.class);
//		busAgent.autoAllConnect(slModelUndoProxy);
//        /*
//            (this, slModelUndoProxy,slgView, frame, undoer);
//		*/
//        //slgView.setController(slgController);
//		//busAgent.connect(slModel, "Listener");
//        slgController = new SLGController
//            (this, slModelUndoProxy,slgView, frame, undoer);
//		
//		
//		slgController.init(true);
//		//busAgent.connect(slgController, "Model", slModel, "Controller");
//		busAgent.connect(slgController, "Model", slModel, null);
//		busAgent.connect(slgView, "Controller", slgController, null);
//		return retVal;

   


}
