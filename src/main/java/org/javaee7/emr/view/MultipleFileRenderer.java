package org.javaee7.emr.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.myfaces.renderkit.html.HtmlInputFileRenderer;
import org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared.renderkit.html.util.HttpPartWrapper;

public class MultipleFileRenderer extends HtmlInputFileRenderer {

    public MultipleFileRenderer() {
        super();
    }

    @Override
    public void decode(FacesContext facesContext, UIComponent component) {

        try {
            String clientId = component.getClientId();

            ExternalContext externalContext = facesContext.getExternalContext();
            Map<String, String> requestMap = externalContext.getRequestParameterMap();

            if (requestMap.containsKey(clientId)) {
                ((UIInput) component).setSubmittedValue(requestMap.get(clientId));
            }

            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

            Collection<Part> parts = request.getParts();
            List<Part> multiple = new ArrayList<Part>();
            for (Part cur : parts) {
                if (clientId.equals(cur.getName())) {
                    component.setTransient(true);
                    multiple.add(new HttpPartWrapper(cur));
                }
            }
            ((UIInput) component).setSubmittedValue(multiple);
        } catch (IOException e) {
            throw new FacesException(e);
        } catch (ServletException e) {
            throw new FacesException(e);
        }

        if (component instanceof ClientBehaviorHolder && !HtmlRendererUtils.isDisabled(component)) {
            HtmlRendererUtils.decodeClientBehaviors(facesContext, component);
        }
    }

}
