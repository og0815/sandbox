/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.jira.client;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Filter;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import java.net.URI;

/**
 *
 * @author oliver.guenther
 */
public class Main {
    
    public static void main(String[] args) {
        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        JiraRestClient client = factory.createWithBasicHttpAuthentication(URI.create("https://xxxxx"), "yyyyy", "yyyy");        
        Filter filter = client.getSearchClient().getFilter(14903).claim();
        SearchResult sr = client.getSearchClient().searchJql(filter.getJql()).claim();
        
        for (Issue issue : sr.getIssues()) {
            System.out.println(issue.getKey() + " | " + issue.getSummary());
        }
        
        
    }
}
