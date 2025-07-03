package com.mydating.dating.util;

import java.util.Comparator;

import com.mydating.dating.dto.MatchingUser;

public class UserSorting implements Comparator<MatchingUser>{

	@Override
	public int compare(MatchingUser o1, MatchingUser o2) {
		if(o1.getAgediff()<o2.getAgediff())
			return -1;
		else if(o1.getAgediff()>o2.getAgediff())
			return 1;
		else {
			if(o1.getMatchingInterestCount() > o2.getMatchingInterestCount())
				return -1;
			else if(o1.getMatchingInterestCount()<o2.getMatchingInterestCount())
				return 1;
			return 0;
		}
	}
	
}
