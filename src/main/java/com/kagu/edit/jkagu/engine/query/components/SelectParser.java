package com.kagu.edit.jkagu.engine.query.components;

import com.kagu.edit.jkagu.engine.query.Cons;
import com.kagu.edit.jkagu.engine.query.From;
import com.kagu.edit.jkagu.engine.query.components.select.*;

public class SelectParser {

    public final static SelectOperation findSelect(String text)
    {
        final SelectOperation result;
        int selectIndex = text.indexOf(Cons.SELECT);

        if(selectIndex == -1)
        {
            return new OneRowOperation();
        }

        int fromIndex = text.indexOf(Cons.FROM);

        String selectValue = text.substring(selectIndex + Cons.SELECT.length(), fromIndex - 1).trim();

        /**
         * Here return Message to the user.
         */
        if(null == selectValue || "".equals(selectValue))
        {
            throw new IllegalArgumentException("There is no select value!");
        }

        if(selectValue.equals("*") || selectValue.equals("row") || selectValue.equals("line"))
        {
            result = new OneRowOperation();
        }
        else
        {
            int starIndex = selectValue.indexOf('*');
            if(starIndex == -1)
            {
                throw new IllegalArgumentException("There is no * character in the selection!");
            }


            if(starIndex == 0)
            {
                final String selection = selectValue.substring(1, selectValue.length());
                result = new FromStartToIndex(selection);
            }
            else if(starIndex == selectValue.length() - 1)
            {
                final String selection = selectValue.substring(0, selectValue.length() - 1);
                result = new FromIndexToEnd(selection);
            }
            else
            {
                final String selectionBeggining = selectValue.substring(0, starIndex);
                final String selectionEnd = selectValue.substring(starIndex +1, selectValue.length());

                result = new Between(selectionBeggining, selectionEnd);
            }

        }

        return result;
    }



    private static From findFrom(String text)
    {
        From result = null;

        int fromIndex = text.indexOf(Cons.FROM);
        int whereIndex = text.indexOf(Cons.WHERE);


        String fromValue = text.substring(fromIndex + Cons.FROM.length(), whereIndex - 1).trim();

        boolean found = false;
        for(From f : From.values())
        {
            String name = f.name();
            if(fromValue.equalsIgnoreCase(name))
            {
                result = f;
                found = true;
                break;
            }
        }


        /**
         * Here return Message to the user.
         */
        if(!found)
        {
            throw new IllegalArgumentException("Wrong from value: " +  fromValue);
        }

        return result;
    }

}
