package org.opennms.vaadin.applicationstack.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.opennms.vaadin.applicationstack.model.ApplicationLayer;
import org.opennms.vaadin.applicationstack.model.ApplicationStack;
import org.opennms.vaadin.applicationstack.model.Criteria;
import org.opennms.vaadin.applicationstack.model.Criteria.EntityType;
import org.opennms.vaadin.applicationstack.model.NodeDummy;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Theme("applicationstack")
public class ApplicationStackUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);
        setSizeFull();

        final ApplicationStack stack = createDummyApplicationStack(); //loadApplicationStackFromFile();
        if (stack != null) {
            registerDummyNodes(stack);
            layout.addComponent(new ApplicationStackComponent().render(stack));
        } else {
            Notification.show("Could not load application stack file from disk", Notification.Type.ERROR_MESSAGE);
        }
    }

    private static void registerDummyNodes(ApplicationStack stack) {
        for (ApplicationLayer eachLayer : stack.getLayers()) {
            int max = 100;
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                int good = random.nextInt(max) + 1;
                max -= good - 1;
                int problems = random.nextInt(max) + 1;
                max -= problems - 1;
                int death = random.nextInt(max) + 1;
                stack.registerNode(eachLayer.getLabel(), new NodeDummy("node" + (i + 1), good, death, problems));
            }
        }
    }

    private ApplicationStack loadApplicationStackFromFile() {
        File file = new File("/home/marskuh/Desktop/test.xml");
        try {
            if (!file.exists()) {
                Marshaller marshaller;
                marshaller = JAXBContext.newInstance(ApplicationStack.class).createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(createDummyApplicationStack(), file);
            }
            return (ApplicationStack) JAXBContext.newInstance(ApplicationStack.class).createUnmarshaller().unmarshal(file);
        } catch (JAXBException ex) {
            Logger.getLogger(ApplicationStackUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ApplicationStack createDummyApplicationStack() {
        return new ApplicationStack("Waschmasch3ine")
                .addLayer(new ApplicationLayer("Layer1", 0, 0, 4, 1)
                    .addCriteria(new Criteria(EntityType.Category, Criteria.Operator.Equals, "*")))
                .addLayer(new ApplicationLayer("Layer2a", 1, 0, 2, 1))
                .addLayer(new ApplicationLayer("Layer2b", 1, 2, 2, 1))
                .addLayer(new ApplicationLayer("Layer3", 2, 0, 4, 1))
                .addLayer(new ApplicationLayer("Layer4", 0, 4, 1, 3))
                .addLayer(new ApplicationLayer("Layer5", 0, 5, 1, 3))
                .addLayer(new ApplicationLayer("Layer6a", 3, 0, 2, 1))
                .addLayer(new ApplicationLayer("Layer6b", 3, 2, 1, 1))
                .addLayer(new ApplicationLayer("Layer6c", 3, 3, 1, 1))
                .addLayer(new ApplicationLayer("Layer6d", 3, 4, 2, 1))
                .addLayer(new ApplicationLayer("Layer7", 4, 0, 6, 1))
                .addLayer(new ApplicationLayer("Layer8a", 5, 0, 1, 2))
                .addLayer(new ApplicationLayer("Layer8b", 5, 1, 1, 2))
                .addLayer(new ApplicationLayer("Layer8c", 5, 2, 1, 2))
                .addLayer(new ApplicationLayer("Layer8d", 5, 3, 1, 2))
                .addLayer(new ApplicationLayer("Layer8e_1", 5, 4, 1, 1))
                .addLayer(new ApplicationLayer("Layer8e_2", 6, 4, 1, 1))
                .addLayer(new ApplicationLayer("Layer8f", 5, 5, 1, 2))
                .addLayer(new ApplicationLayer("Layer9", 7, 0, 6, 1))
                .addLayer(new ApplicationLayer("Layer10a", 8, 0, 3, 1))
                .addLayer(new ApplicationLayer("Layer10b", 8, 3, 3, 1))
                .addLayer(new ApplicationLayer("Layer11", 9, 0, 6, 1));
    }
}