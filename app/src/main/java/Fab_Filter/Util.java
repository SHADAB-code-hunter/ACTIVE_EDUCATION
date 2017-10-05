package Fab_Filter;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import pojo.Cat_Model;
import utilities.MyApplication;

/**
 * Created by krupenghetiya on 27/06/17.
 */

public class Util {

/*
    public static MovieData getMovies(){

        List<SingleMovie> mList = new ArrayList<>();
        mList.add(new SingleMovie("Manav Rachna University","https://yts.ag/movie/smurfs-the-lost-village-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Adventure","720p",2017,5.8f));
        mList.add(new SingleMovie("House of Strangers","https://yts.ag/movie/house-of-strangers-1949","http://activequizindia.com/images/banner/amity_noida.jpg","Crime","1080p",1949,7.4f));
        mList.add(new SingleMovie("Bonjour Tristesse","https://yts.ag/movie/bonjour-tristesse-1958","http://activequizindia.com/images/banner/amity_noida.jpg","Drama","3D",1958,7.0f));
        mList.add(new SingleMovie("Dragonwyck","https://yts.ag/movie/dragonwyck-1946","http://activequizindia.com/images/banner/amity_noida.jpg","Drama","720p",1946,7.0f));
        mList.add(new SingleMovie("Barabbas","https://yts.ag/movie/barabbas-1961","http://activequizindia.com/images/banner/amity_noida.jpg","Adventure","1080p",1961,7.0f));
        mList.add(new SingleMovie("Ghost in the Shell Arise: Border 4 - Ghost Stands Alone","https://yts.ag/movie/ghost-in-the-shell-arise-border-4-ghost-stands-alone-2014","http://activequizindia.com/images/banner/amity_noida.jpg","Action","3D",2014,7.4f));
        mList.add(new SingleMovie("Ghost in the Shell Arise: Border 3 - Ghost Tears","https://yts.ag/movie/ghost-in-the-shell-arise-border-3-ghost-tears-2014","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",2014,7.3f));
        mList.add(new SingleMovie("Extortion","https://yts.ag/movie/extortion-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2017,6.2f));
        mList.add(new SingleMovie("Is Genesis History?","https://yts.ag/movie/is-genesis-history-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Documentary","3D",2017,4.6f));
        mList.add(new SingleMovie("Whoever Slew Auntie Roo?","https://yts.ag/movie/whoever-slew-auntie-roo-1972","http://activequizindia.com/images/banner/amity_noida.jpg","Horror","720p",1972,6.2f));
        mList.add(new SingleMovie("Moontrap: Target Earth","https://yts.ag/movie/moontrap-target-earth-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2017,2.8f));
        mList.add(new SingleMovie("Kong: Skull Island","https://yts.ag/movie/kong-skull-island-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","3D",2017,6.9f));
        mList.add(new SingleMovie("Trail of the Pink Panther","https://yts.ag/movie/trail-of-the-pink-panther-1982","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","720p",1982,4.9f));
        mList.add(new SingleMovie("The Babymoon","https://yts.ag/movie/the-babymoon-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2017,4.9f));
        mList.add(new SingleMovie("Rabbit Hole","https://yts.ag/movie/rabbit-hole-2010","http://activequizindia.com/images/banner/amity_noida.jpg","Drama","3D",2010,7.0f));
        mList.add(new SingleMovie("Who'll Stop the Rain","https://yts.ag/movie/wholl-stop-the-rain-1978","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",1978,6.8f));
        mList.add(new SingleMovie("Revenge of the Pink Panther","https://yts.ag/movie/revenge-of-the-pink-panther-1978","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","1080p",1978,6.7f));
        mList.add(new SingleMovie("Apprentice","https://yts.ag/movie/apprentice-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Drama","3D",2016,6.7f));
        mList.add(new SingleMovie("The Food Guide to Love","https://yts.ag/movie/the-food-guide-to-love-2013","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","720p",2013,5.8f));
        mList.add(new SingleMovie("Youth in Revolt","https://yts.ag/movie/youth-in-revolt-2009","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","1080p",2009,6.5f));
        mList.add(new SingleMovie("Gods and Generals","https://yts.ag/movie/gods-and-generals-2003","http://activequizindia.com/images/banner/amity_noida.jpg","Drama","3D",2003,6.3f));
        mList.add(new SingleMovie("D3: The Mighty Ducks","https://yts.ag/movie/d3-the-mighty-ducks-1996","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",1996,5.3f));
        mList.add(new SingleMovie("Airport","https://yts.ag/movie/airport-1970","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",1970,6.6f));
        mList.add(new SingleMovie("The Zookeeper's Wife","https://yts.ag/movie/the-zookeepers-wife-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Biography","3D",2017,7.0f));
        mList.add(new SingleMovie("Evil Ed","https://yts.ag/movie/evil-ed-1995","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","720p",1995,5.5f));
        mList.add(new SingleMovie("Needful Things","https://yts.ag/movie/needful-things-1993","http://activequizindia.com/images/banner/amity_noida.jpg","Crime","1080p",1993,6.2f));
        mList.add(new SingleMovie("Red Dog: True Blue","https://yts.ag/movie/red-dog-true-blue-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","3D",2016,6.6f));
        mList.add(new SingleMovie("Jawbreaker","https://yts.ag/movie/jawbreaker-1999","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",1999,5.5f));
        mList.add(new SingleMovie("Alone in Berlin","https://yts.ag/movie/alone-in-berlin-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2016,0.0f));
        mList.add(new SingleMovie("Song to Song","https://yts.ag/movie/song-to-song-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Drama","3D",2017,6.2f));
        mList.add(new SingleMovie("Power Rangers","https://yts.ag/movie/power-rangers-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",2017,0.0f));
        mList.add(new SingleMovie("Immortal Beloved","https://yts.ag/movie/immortal-beloved-1994","http://activequizindia.com/images/banner/amity_noida.jpg","Biography","1080p",1994,7.5f));
        mList.add(new SingleMovie("The Eagle Huntress","https://yts.ag/movie/the-eagle-huntress-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Adventure","3D",2016,7.5f));
        mList.add(new SingleMovie("CHIPS","https://yts.ag/movie/chips-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",2017,5.8f));
        mList.add(new SingleMovie("Jawbone","https://yts.ag/movie/jawbone-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2017,6.5f));
        mList.add(new SingleMovie("Dragonheart: Battle for the Heartfire","https://yts.ag/movie/dragonheart-battle-for-the-heartfire-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Fantasy","3D",2017,5.2f));
        mList.add(new SingleMovie("Das Boot","https://yts.ag/movie/das-boot-1981","http://activequizindia.com/images/banner/amity_noida.jpg","Adventure","720p",1981,8.4f));
        mList.add(new SingleMovie("The Belko Experiment","https://yts.ag/movie/the-belko-experiment-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2016,6.1f));
        mList.add(new SingleMovie("Life","https://yts.ag/movie/life-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Horror","3D",2017,6.7f));
        mList.add(new SingleMovie("The Carer","https://yts.ag/movie/the-carer-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","720p",2016,6.5f));
        mList.add(new SingleMovie("Altitude","https://yts.ag/movie/altitude-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2017,5.0f));
        mList.add(new SingleMovie("Wilson","https://yts.ag/movie/wilson-2017","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","3D",2017,5.8f));
        mList.add(new SingleMovie("On the Double","https://yts.ag/movie/on-the-double-1961","http://activequizindia.com/images/banner/amity_noida.jpg","Adventure","720p",1961,6.5f));
        mList.add(new SingleMovie("A Date with Miss Fortune","https://yts.ag/movie/a-date-with-miss-fortune-2015","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","1080p",2015,5.5f));
        mList.add(new SingleMovie("Prevenge","https://yts.ag/movie/prevenge-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Comedy","3D",2016,6.0f));
        mList.add(new SingleMovie("We Go On","https://yts.ag/movie/we-go-on-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Action","720p",2016,0.0f));
        mList.add(new SingleMovie("The Legend of Ben Hall","https://yts.ag/movie/the-legend-of-ben-hall-2016","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2016,6.0f));
        mList.add(new SingleMovie("Sniper","https://yts.ag/movie/sniper-1993","http://activequizindia.com/images/banner/amity_noida.jpg","Action","3D",1993,0.0f));
        mList.add(new SingleMovie("The Last Flight of Noah's Ark","https://yts.ag/movie/the-last-flight-of-noahs-ark-1980","http://activequizindia.com/images/banner/amity_noida.jpg","Adventure","720p",1980,5.8f));
        mList.add(new SingleMovie("I Tawt I Taw a Puddy Tat","https://yts.ag/movie/i-tawt-i-taw-a-puddy-tat-2011","http://activequizindia.com/images/banner/amity_noida.jpg","na","1080p",2011,6.5f));
        mList.add(new SingleMovie("I Tawt I Taw a Puddy Tat","https://yts.ag/movie/i-tawt-i-taw-a-puddy-tat-2011","http://activequizindia.com/images/banner/amity_noida.jpg","Action","1080p",2011,6.5f));

       */
/* mList.add(new SingleMovie("Tell Me How I Die","https://yts.ag/movie/tell-me-how-i-die-2016","https://yts.ag/assets/images/movies/tell_me_how_i_die_2016/medium-cover.jpg","na","720p",2016,5.0f));
        mList.add(new SingleMovie("12 Rounds","https://yts.ag/movie/12-rounds-2009","https://yts.ag/assets/images/movies/12_rounds_2009/medium-cover.jpg","na","1080p",2009,5.6f));
        mList.add(new SingleMovie("I Am Heath Ledger","https://yts.ag/movie/i-am-heath-ledger-2017","https://yts.ag/assets/images/movies/i_am_heath_ledger_2017/medium-cover.jpg","na","3D",2017,7.4f));
        mList.add(new SingleMovie("Table 19","https://yts.ag/movie/table-19-2017","https://yts.ag/assets/images/movies/table_19_2017/medium-cover.jpg","na","720p",2017,5.8f));
        mList.add(new SingleMovie("The LEGO Batman Movie","https://yts.ag/movie/the-lego-batman-movie-2017","https://yts.ag/assets/images/movies/the_lego_batman_movie_2017/medium-cover.jpg","na","1080p",2017,7.4f));
        mList.add(new SingleMovie("John Wick: Chapter 2","https://yts.ag/movie/john-wick-chapter-2-2017","https://yts.ag/assets/images/movies/john_wick_chapter_2_2017/medium-cover.jpg","na","3D",2017,7.8f));
        mList.add(new SingleMovie("The Sea Shall Not Have Them","https://yts.ag/movie/the-sea-shall-not-have-them-1954","https://yts.ag/assets/images/movies/the_sea_shall_not_have_them_1954/medium-cover.jpg","na","720p",1954,6.5f));
        mList.add(new SingleMovie("How to Murder Your Wife","https://yts.ag/movie/how-to-murder-your-wife-1965","https://yts.ag/assets/images/movies/how_to_murder_your_wife_1965/medium-cover.jpg","na","1080p",1965,0.0f));
        mList.add(new SingleMovie("Alvarez Kelly","https://yts.ag/movie/alvarez-kelly-1966","https://yts.ag/assets/images/movies/alvarez_kelly_1966/medium-cover.jpg","na","3D",1966,6.4f));
        mList.add(new SingleMovie("The Vagrant","https://yts.ag/movie/the-vagrant-1992","https://yts.ag/assets/images/movies/the_vagrant_1992/medium-cover.jpg","na","720p",1992,5.8f));
        mList.add(new SingleMovie("Queen Rock Montreal & Live Aid","https://yts.ag/movie/queen-rock-montreal-live-aid-2007","https://yts.ag/assets/images/movies/queen_rock_montreal_live_aid_2007/medium-cover.jpg","na","1080p",2007,0.0f));
        mList.add(new SingleMovie("Overboard","https://yts.ag/movie/overboard-1987","https://yts.ag/assets/images/movies/overboard_1987/medium-cover.jpg","na","3D",1987,6.8f));
        mList.add(new SingleMovie("Peaceful Warrior","https://yts.ag/movie/peaceful-warrior-2006","https://yts.ag/assets/images/movies/peaceful_warrior_2006/medium-cover.jpg","na","720p",2006,7.3f));
        mList.add(new SingleMovie("The Axe Murders of Villisca","https://yts.ag/movie/the-axe-murders-of-villisca-2016","https://yts.ag/assets/images/movies/the_axe_murders_of_villisca_2016/medium-cover.jpg","na","1080p",2016,4.3f));
        mList.add(new SingleMovie("Viking Legacy","https://yts.ag/movie/viking-legacy-2016","https://yts.ag/assets/images/movies/viking_legacy_2016/medium-cover.jpg","na","3D",2016,2.6f));
        mList.add(new SingleMovie("Child of Satan","https://yts.ag/movie/child-of-satan-2017","https://yts.ag/assets/images/movies/child_of_satan_2017/medium-cover.jpg","na","720p",2017,2.1f));
        mList.add(new SingleMovie("Voice from the Stone","https://yts.ag/movie/voice-from-the-stone-2017","https://yts.ag/assets/images/movies/voice_from_the_stone_2017/medium-cover.jpg","na","1080p",2017,5.3f));
        mList.add(new SingleMovie("The Last Word","https://yts.ag/movie/the-last-word-2017","https://yts.ag/assets/images/movies/the_last_word_2017/medium-cover.jpg","na","3D",2017,6.5f));
        mList.add(new SingleMovie("Swallows and Amazons","https://yts.ag/movie/swallows-and-amazons-2016","https://yts.ag/assets/images/movies/swallows_and_amazons_2016/medium-cover.jpg","na","720p",2016,6.2f));
        mList.add(new SingleMovie("A Cure for Wellness","https://yts.ag/movie/a-cure-for-wellness-2016","https://yts.ag/assets/images/movies/a_cure_for_wellness_2016/medium-cover.jpg","na","1080p",2016,6.5f));
        mList.add(new SingleMovie("McLaren","https://yts.ag/movie/mclaren-2017","https://yts.ag/assets/images/movies/mclaren_2016/medium-cover.jpg","na","3D",2017,0.0f));
        mList.add(new SingleMovie("The Ticket","https://yts.ag/movie/the-ticket-2016","https://yts.ag/assets/images/movies/the_ticket_2016/medium-cover.jpg","na","720p",2016,5.3f));
        mList.add(new SingleMovie("Limelight","https://yts.ag/movie/limelight-1952","https://yts.ag/assets/images/movies/limelight_1952/medium-cover.jpg","na","1080p",1952,8.1f));
        mList.add(new SingleMovie("Bonded by Blood 2","https://yts.ag/movie/bonded-by-blood-2-2017","https://yts.ag/assets/images/movies/bonded_by_blood_2_2017/medium-cover.jpg","na","3D",2017,7.4f));
        mList.add(new SingleMovie("T2 Trainspotting","https://yts.ag/movie/t2-trainspotting-2017","https://yts.ag/assets/images/movies/t2_trainspotting_2017/medium-cover.jpg","na","720p",2017,7.4f));
        mList.add(new SingleMovie("The Last Face","https://yts.ag/movie/the-last-face-2016","https://yts.ag/assets/images/movies/the_last_face_2016/medium-cover.jpg","na","1080p",2016,3.9f));
        mList.add(new SingleMovie("The Assignment","https://yts.ag/movie/the-assignment-2016","https://yts.ag/assets/images/movies/the_assignment_2016/medium-cover.jpg","na","3D",2016,4.5f));
        mList.add(new SingleMovie("Aftermath","https://yts.ag/movie/aftermath-2017","https://yts.ag/assets/images/movies/aftermath_2017/medium-cover.jpg","na","720p",2017,5.7f));
        mList.add(new SingleMovie("Night of Something Strange","https://yts.ag/movie/night-of-something-strange-2016","https://yts.ag/assets/images/movies/night_of_something_strange_2016/medium-cover.jpg","na","1080p",2016,4.8f));
        mList.add(new SingleMovie("Molot","https://yts.ag/movie/molot-2016","https://yts.ag/assets/images/movies/molot_2016/medium-cover.jpg","na","3D",2016,4.7f));
        mList.add(new SingleMovie("The Devil's Candy","https://yts.ag/movie/the-devils-candy-2015","https://yts.ag/assets/images/movies/the_devils_candy_2015/medium-cover.jpg","na","720p",2015,6.5f));
        mList.add(new SingleMovie("Teen Witch","https://yts.ag/movie/teen-witch-1989","https://yts.ag/assets/images/movies/teen_witch_1989/medium-cover.jpg","na","1080p",1989,6.2f));
        mList.add(new SingleMovie("Off Piste","https://yts.ag/movie/off-piste-2016","https://yts.ag/assets/images/movies/off_piste_2016/medium-cover.jpg","na","3D",2016,4.4f));
        mList.add(new SingleMovie("Ana-ta-han","https://yts.ag/movie/ana-ta-han-1953","https://yts.ag/assets/images/movies/ana_ta_han_1953/medium-cover.jpg","na","720p",1953,7.5f));
        mList.add(new SingleMovie("Bang Bang Baby","https://yts.ag/movie/bang-bang-baby-2014","https://yts.ag/assets/images/movies/bang_bang_baby_2014/medium-cover.jpg","na","1080p",2014,5.0f));
        mList.add(new SingleMovie("Logan","https://yts.ag/movie/logan-2017","https://yts.ag/assets/images/movies/logan_2017/medium-cover.jpg","na","3D",2017,8.3f));
        mList.add(new SingleMovie("Rita, Sue and Bob Too","https://yts.ag/movie/rita-sue-and-bob-too-1987","https://yts.ag/assets/images/movies/rita_sue_and_bob_too_1987/medium-cover.jpg","na","720p",1987,6.5f));
        mList.add(new SingleMovie("MacArthur","https://yts.ag/movie/macarthur-1977","https://yts.ag/assets/images/movies/macarthur_1977/medium-cover.jpg","na","1080p",1977,6.6f));
        mList.add(new SingleMovie("King Arthur and the Knights of the Round Table","https://yts.ag/movie/king-arthur-and-the-knights-of-the-round-table-2017","https://yts.ag/assets/images/movies/king_arthur_and_the_knights_of_the_round_table_2017/medium-cover.jpg","na","3D",2017,2.5f));
        mList.add(new SingleMovie("Albion: The Enchanted Stallion","https://yts.ag/movie/albion-the-enchanted-stallion-2016","https://yts.ag/assets/images/movies/albion_the_enchanted_stallion_2016/medium-cover.jpg","na","720p",2016,4.8f));
        mList.add(new SingleMovie("The Comedian","https://yts.ag/movie/the-comedian-2016","https://yts.ag/assets/images/movies/the_comedian_2016/medium-cover.jpg","na","1080p",2016,0.0f));
        mList.add(new SingleMovie("Kill'em All","https://yts.ag/movie/killem-all-2017","https://yts.ag/assets/images/movies/killem_all_2017/medium-cover.jpg","na","3D",2017,4.2f));
        mList.add(new SingleMovie("M.S. Dhoni: The Untold Story","https://yts.ag/movie/m-s-dhoni-the-untold-story-2016","https://yts.ag/assets/images/movies/m_s_dhoni_the_untold_story_2016/medium-cover.jpg","na","720p",2016,7.8f));
        mList.add(new SingleMovie("Knucklebones","https://yts.ag/movie/knucklebones-2016","https://yts.ag/assets/images/movies/knucklebones_2016/medium-cover.jpg","na","1080p",2016,3.8f));
        mList.add(new SingleMovie("Beauty and the Beast","https://yts.ag/movie/beauty-and-the-beast-2017","https://yts.ag/assets/images/movies/beauty_and_the_beast_2017/medium-cover.jpg","na","3D",2017,7.5f));
        mList.add(new SingleMovie("The Mephisto Waltz","https://yts.ag/movie/the-mephisto-waltz-1971","https://yts.ag/assets/images/movies/the_mephisto_waltz_1971/medium-cover.jpg","na","720p",1971,6.0f));
        mList.add(new SingleMovie("The Goose Steps Out","https://yts.ag/movie/the-goose-steps-out-1942","https://yts.ag/assets/images/movies/the_goose_steps_out_1942/medium-cover.jpg","na","1080p",1942,7.0f));
        mList.add(new SingleMovie("The California Kid","https://yts.ag/movie/the-california-kid-1974","https://yts.ag/assets/images/movies/the_california_kid_1974/medium-cover.jpg","na","3D",1974,6.6f));
        mList.add(new SingleMovie("My Cousin Vinny","https://yts.ag/movie/my-cousin-vinny-1992","https://yts.ag/assets/images/movies/my_cousin_vinny_1992/medium-cover.jpg","na","720p",1992,7.5f));
        mList.add(new SingleMovie("Mean Dreams","https://yts.ag/movie/mean-dreams-2016","https://yts.ag/assets/images/movies/mean_dreams_2016/medium-cover.jpg","na","1080p",2016,6.3f));
*//*


        return new MovieData(mList);

    }
*/

    public static MovieData getList() {

        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences("Temp_List",0);
        String str_list=sharedPreferences.getString("shrd_list", "na");
        Gson gson = new Gson();java.lang.reflect.Type type;type = new TypeToken<List<Cat_Model>>() {}.getType();
        List<Cat_Model> Cat_List = (List<Cat_Model>) gson.fromJson(str_list, type);
        return new MovieData(Cat_List);
    }
}
