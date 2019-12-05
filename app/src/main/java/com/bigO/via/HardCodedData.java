package com.bigO.via;

public class HardCodedData {

    private static EventDuration[] eventTimeData = {
            new EventDuration(18,00,18,30), // audi r8 raffle draw
            new EventDuration(14,00,15,00), // ferrari portofino reveal
            new EventDuration(14,30,15,30), // model of the year
            new EventDuration(15,45,16,45), // newcomer of the year

    };

    private static String[] placeBlurbs = {
            "Location: Audi Showcase\n\nOne lucky winner will drive away with a brand new Audi R8 in Audi's annual raffle draw",
            "Location: Ferrari Showcase\n\nCome see Ferrari unveil their long-awaited Portofino model!",
            "Location: Central Stage\n\nThe winner of the 2020 Model of the Year will be unveiled! The award is given to the model that exceeds expectations in all areas of automobile service for the year of 2019",
            "Location: Central Stage\n\nThe winner of the 2020 Newcomer of the Year will be unveiled! The award will be presented to the vehicle which has taken the auto world by storm in the past year.",
            "Audi AG is a German automobile manufacturer that designs, engineers, produces, markets and distributes luxury vehicles. Audi is a member of the Volkswagen Group and has its roots at Ingolstadt, Bavaria, Germany. Audi-branded vehicles are produced in nine production facilities worldwide.",
            "The central stage is where the very best vehicles are showcased. It is also the site where awards are presented",
            "Ferrari is an Italian luxury sports car manufacturer based in Maranello. Founded by Enzo Ferrari in 1939 out of Alfa Romeo's race division as Auto Avio Costruzioni, the company built its first car in 1940.",
            "Ford Motor Company is an American multinational automaker that has its main headquarters in Dearborn, Michigan, a suburb of Detroit. It was founded by Henry Ford and incorporated on June 16, 1903. The company sells automobiles and commercial vehicles under the Ford brand and most luxury cars under the Lincoln brand.",
            "Honda Motor Company is a Japanese public multinational conglomerate corporation primarily known as a manufacturer of automobiles and motorcycles.",
            "The Hyundai Motor Company, commonly known as Hyundai Motors is a South Korean multinational automotive manufacturer headquartered in Seoul",
            "Jaguar is the luxury vehicle brand of Jaguar Land Rover, a British multinational car manufacturer with its headquarters in Whitley, Coventry, England.",
            "Mercedes-Benz is a German global automobile marque and a division of Daimler AG. Mercedes-Benz is known for luxury vehicles, buses, coaches, ambulances and trucks. The headquarters is in Stuttgart, Baden-Württemberg. The name first appeared in 1926 under Daimler-Benz.",
            "Tesla, Inc., is an American automotive and energy company based in Palo Alto, California. The company specializes in electric car manufacturing and, through its SolarCity subsidiary, solar panel manufacturing.",
            "Volvo Cars is a luxury vehicles brand and a subsidiary of the Chinese automotive company Geely. It is headquartered in Torslanda in Gothenburg, Sweden"
    };

    public static EventDuration[] getEventTimeData() {
        return eventTimeData;
    }

    public static String[] getPlaceBlurbs() {return placeBlurbs;}


}
