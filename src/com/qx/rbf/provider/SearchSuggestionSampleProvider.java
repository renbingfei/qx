package com.qx.rbf.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionSampleProvider extends
		SearchRecentSuggestionsProvider {

	public final static String AUTHORITY="com.qx.rbf.provider.SearchSuggestionProvider";
	public final static String AUTHORITYForShop="com.qx.rbf.provider.SearchSuggestionProviderForShop";
	public final static int MODE=DATABASE_MODE_QUERIES;
	
	public SearchSuggestionSampleProvider(){
		super();
		setupSuggestions(AUTHORITY, MODE);
	}
}
