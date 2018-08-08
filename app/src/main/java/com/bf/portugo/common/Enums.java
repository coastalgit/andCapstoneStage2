package com.bf.portugo.common;

/*
 * @author frielb
 * Created on 31/07/2018
 */
public class Enums {

    public enum LanguageLocale{
        ENGLISH {public String toString(){return "en-UK";}},
        PORTUGUESE {public String toString(){return "pt-PT";}}
    }

    public enum VerbFilter{
        ALL,
        ESSENTIAL
    }

    public enum VerbPrefix_EN{
        I {public String toString(){return "I";}},
        YOU {public String toString(){return "You";}},
        HESHE {public String toString(){return "He/She";}},
        WE {public String toString(){return "We";}},
        THEY {public String toString(){return "They";}},
    }

    public enum VerbPrefix_PT{
        I {public String toString(){return "Eu";}},
        YOU {public String toString(){return "Tu";}},
        HESHE {public String toString(){return "Ele/Ela/Voće";}},
        WE {public String toString(){return "Nós";}},
        THEY {public String toString(){return "Eles/Elas/Voćes";}},
    }

    public enum ErrorCode {
        UNKNOWN,
        CONNECTION_ERROR,
        WORK_IN_PROGRESS,
        INVALID_DATA,
        INVALID_RESPONSE
    }

/*
    public enum TMDBQueryBy {
        POPULAR {public String toString(){return "popular";}},
        TOPRATED {public String toString(){return "top_rated";}}
    }

    public enum TMDBErrorCode {
        @SuppressWarnings("unused")UNKNOWN,
        CONNECTION_ERROR,
        INVALID_DATA,
        INVALID_RESPONSE
    }
*/

}
