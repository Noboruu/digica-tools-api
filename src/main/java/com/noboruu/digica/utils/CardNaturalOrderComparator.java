package com.noboruu.digica.utils;

import com.noboruu.digica.model.dto.CardDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardNaturalOrderComparator implements Comparator<CardDTO> {
    private static final Pattern CARD_REGEX_MATCHER = Pattern.compile("^([a-zA-Z0-9]+)-?([0-9]+)?$");

    @Override
    public int compare(CardDTO o1, CardDTO o2) {
        String c1 = o1.getCode();
        String c2 = o2.getCode();

        Matcher m1 = CARD_REGEX_MATCHER.matcher(c1);
        Matcher m2 = CARD_REGEX_MATCHER.matcher(c2);

        if(!(m1.find() && m2.find())) {
            return 0;
        }

        int n1 = StringUtils.isBlank(m1.group(2)) ? 0 : Integer.parseInt(m1.group(2));
        int n2 = StringUtils.isBlank(m2.group(2)) ? 0 : Integer.parseInt(m2.group(2));

        return Integer.compare(n1, n2);
    }
}
