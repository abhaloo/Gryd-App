package com.bigO.via;

public class HardCodedData {

    private static EventDuration []  eventTimeData = {
            new EventDuration(18,00,18,30), // audi r8 raffle draw
            new EventDuration(14,00,15,00), // ferrari portofino reveal
            new EventDuration(14,30,15,30), // model of the year
            new EventDuration(15,45,16,45), // newcomer of the year

    };

    public static EventDuration[] getEventTimeData() {
        return eventTimeData;
    }


}
