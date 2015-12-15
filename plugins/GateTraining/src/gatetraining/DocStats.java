/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gatetraining;

import gate.FeatureMap;
import gate.Resource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;

/**
 *
 * @author DChandran
 */
@CreoleResource(name = "Document statistics")
public class DocStats extends AbstractLanguageAnalyser {

    private String annotationSetName;

    public String getAnnotationSetName() {
        return annotationSetName;
    }

    @Optional
    @RunTime
    @CreoleParameter
    public void setAnnotationSetName(String annotationSetName) {
        this.annotationSetName = annotationSetName;
    }
    
    private String annotationType;

    public String getAnnotationType() {
        return annotationType;
    }

    @RunTime
    @CreoleParameter(defaultValue = "Token")
    public void setAnnotationType(String annotationType) {
        this.annotationType = annotationType;
    }
    

    @Override
    public void execute() throws ExecutionException {
        FeatureMap docFeatures = getDocument().getFeatures();
        docFeatures.put(annotationType + "Count",
                getDocument().getAnnotations(annotationSetName).get(annotationType).size());
    }

    @Override
    public Resource init() throws ResourceInstantiationException {
        System.out.println("Created a DocStats");
        return this;
    }

}
