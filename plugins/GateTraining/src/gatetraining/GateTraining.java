/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gatetraining;

import gate.*;
import gate.gui.MainFrame;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingUtilities;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author DChandran
 */
public class GateTraining {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Gate.init();

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                MainFrame.getInstance().setVisible(true);
            }
        });
       Document doc = Factory.newDocument("This is a document!");
       doc.getFeatures().put("test", "successful");

        Document doc2 = Factory.newDocument(new URL("https://gate.ac.uk"), "UTF-8");

        doc2.getAnnotations("test");

       //Utils.addAnn(doc2.getAnnotations("test"), 0L, 10L, "Test", Utils.featureMap());
        DocumentContent content = doc2.getContent();
        Map<String, AnnotationSet> namedAnnotationSets = doc2.getNamedAnnotationSets();
        for (Map.Entry<String, AnnotationSet> entry : namedAnnotationSets.entrySet()) {
            final AnnotationSet annSet = entry.getValue();
            System.out.println("Set " + entry.getKey() + " contains " + annSet.size() + " annotations");
            for (String type : annSet.getAllTypes()) {
                AnnotationSet subset = annSet.get(type);
                System.out.println("  " + type + ": " + subset.size());
            }
        }
        
        Corpus c = Factory.newCorpus("test corpus");
        c.add(doc2);
        
        File pluginsDir = Gate.getPluginsHome();
        File annie = new File(pluginsDir, "ANNIE");
        File file = new File(annie, "ANNIE_with_defaults.gapp");
        CorpusController annielang = (CorpusController)PersistenceManager.loadObjectFromFile(file);
        annielang.setCorpus(c);
        annielang.execute();
    }

}
