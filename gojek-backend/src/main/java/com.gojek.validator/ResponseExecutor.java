package com.gojek.validator;

import com.gojek.client.BaseUrlHelper;
import com.gojek.client.UrlWrap;
import com.gojek.util.IComparator;

public class ResponseExecutor {
    //final Logger logger = LoggerFactory.getLogger(ResponseExecutor.class);
    private final IComparator comparator;
    private final UrlValid urlValidator;
    private final BaseUrlHelper webServiceHelper;

    public ResponseExecutor(IComparator comparator, UrlValid urlValidator, BaseUrlHelper webServiceHelper) {
        this.comparator = comparator;
        this.urlValidator = urlValidator;
        this.webServiceHelper = webServiceHelper;
    }

    public void execute(UrlWrap urlWrapper) {
        String url1 = urlWrapper.getUrl1();
        String url2 = urlWrapper.getUrl2();
        if (!(url1 == null)) {
            if (!(url2 == null)) {
                if (urlValidator.validateUrl(url1) && urlValidator.validateUrl(url2)) {
                    boolean compareResult = comparator.compare(webServiceHelper.getResponseFromRestEndpoint(url1),
                            webServiceHelper.getResponseFromRestEndpoint(url2));
                    if (compareResult) {
                        //logger.info(url1 + "-> equals " + url2);
                        System.out.println(url1 + " equals " + url2);
                    } else {
                        //logger.info(url1 + " not equals " + url2);
                        System.out.println(url1 + " not equals " + url2);
                    }
                } else {
                    System.out.println("Skipping URL : " + url1 + " & " + url2 + " as there is a Malformed URL");
                }
            } else {
                System.out.println("Skipping this iteration as the Url from File2 is null");
            }
        } else {
            System.out.println("Skipping this iteration as the Url from File1 is null");
        }

    }
}


