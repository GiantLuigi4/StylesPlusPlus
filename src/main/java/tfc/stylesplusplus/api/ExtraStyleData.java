package tfc.stylesplusplus.api;

import java.util.ArrayList;

public interface ExtraStyleData {
	ArrayList<ExtraStyle> getExtraStyles();
	void addStyle(ExtraStyle style);
	
	void setSkipParent(boolean value);
	boolean skipParent();
	
	void clear();
}
