package com.example.homework_4_fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class MovieListFragment : Fragment(), OnItemClickListener,
    OnItemLongClickListener {

    var listener: OnMoviesClickListener? = null

    var activity: Activity? = null
    var movieListView: ListView? = null
    var movies: MutableList<RecyclerMoviesItem>? = null
    var movieListAdapter: MoviesAdapter? = null
    var sharedPreference: SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity()
        sharedPreference = SharedPreference()
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_movie_list, container,
            false
        )
        findViewsById(view)
        setMovies()
        movieListAdapter = activity?.let { MoviesAdapter(it, movies) }
        movieListView!!.adapter = movieListAdapter
        movieListView!!.onItemClickListener = this
        movieListView!!.onItemLongClickListener = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = MoviesAdapter(
            LayoutInflater.from(context),
            mutableListOf(
                RecyclerMoviesItem(R.drawable.ford_vs_ferrari, "Форд против Феррари", "Сюжет повествует о " +
                        "группе американских инженеров. В 1960-х под руководством конструктора " +
                        "Кэрролла Шелби и при поддержке британского гонщика Кена Майлса они должны были  " +
                        "с нуля сделать абсолютно новый спорткар, способный опередить Феррари - " +
                        "непобедимого чемпиона 24-часовой гонки на выносливость Ле-Ман", 0),
                RecyclerMoviesItem(R.drawable.holiday, "Каникулы", "Расти примерный семьянин, " +
                        "который очень хочет сплотить семью и воссоздать каникулы из своего детства. " +
                        "Вместе с супругой и двумя сыновьями он решает совершить незабываемое путешествие" +
                        " через всю страну, конечной целью которого станет самый лучший тематический " +
                        "парк Америки. Все тщательно спланировав, они отправляются в дорогу, надеясь, " +
                        "что их ждет очень веселое и увлекательное путешествие", 1),
                RecyclerMoviesItem(R.drawable.pain_and_gain, "Кровью и потом", "Однажды тренеру по фитнесу " +
                        "надоело ходить в трениках. Он решил круто изменить свою судьбу и разбогатеть. " +
                        "Нашел двух других незадачливых качков и предложил им план похищения своего " +
                        "клиента-миллионера. Но если в организме мышц больше, чем мозгов, то даже " +
                        "самый лучший план, подсмотренный в экшен-боевике, может не сработать…", 2),
                RecyclerMoviesItem(R.drawable.bronx_tale, "Бронкская история","Гангстер Санни - большой " +
                        "человек в квартале Бронкса.Убийство, совершенное Санни, положило начало " +
                        "странной дружбе между мафиози и 9 летним подростком Калоджеро. Калоджеро  " +
                        "предстоит выбрать: будет ли он зарабатывать уважение тяжким трудом, как отец, " +
                        "или пойдет по преступному пути, как Санни...", 3),
                RecyclerMoviesItem(R.drawable.carlitos_way, "Путь Карлито", "Один из главарей наркомафии, " +
                        "пуэрториканец Карлито Бриганте, с помощью своего адвоката досрочно выходит на " +
                        "свободу. Карлито - уже немолодой, но всё ещё привлекательный и статный мужчина " +
                        "решает порвать со своим преступным прошлым и начать новую, более праведную " +
                        "жизнь, но возможно ли это...", 4),
                RecyclerMoviesItem(R.drawable.cidad_de_deus, "Город бога", "Город Бога - самый бедный и " +
                        "самый криминальный квартал Рио-де-Жанейро, где нет законов, нет властей, а " +
                        "правила существования устанавливают банды наркоторговцев. Там рождаются с " +
                        "косяком в одной руке и с пушкой - в другой. И для того чтобы выбраться из " +
                        "этого ада, нет другого шанса, кроме совершения преступлений.", 5),
                RecyclerMoviesItem(R.drawable.night_school, "Вечерняя школа", "Группе бездельников придется " +
                        "пройти обучение в вечерней школе, чтобы получить, наконец, аттестат и найти " +
                        "нормальную работу", 6)
            )
        ) {listener?.onMoviesClick(it)}
    }

    private fun setMovies() {
        val movie1 = RecyclerMoviesItem(R.drawable.ford_vs_ferrari, "Форд против Феррари", "Сюжет повествует о " +
                "группе американских инженеров. В 1960-х под руководством конструктора " +
                "Кэрролла Шелби и при поддержке британского гонщика Кена Майлса они должны были  " +
                "с нуля сделать абсолютно новый спорткар, способный опередить Феррари - " +
                "непобедимого чемпиона 24-часовой гонки на выносливость Ле-Ман", 0)
        val movie2 = RecyclerMoviesItem(R.drawable.holiday, "Каникулы", "Расти примерный семьянин, " +
                "который очень хочет сплотить семью и воссоздать каникулы из своего детства. " +
                "Вместе с супругой и двумя сыновьями он решает совершить незабываемое путешествие" +
                " через всю страну, конечной целью которого станет самый лучший тематический " +
                "парк Америки. Все тщательно спланировав, они отправляются в дорогу, надеясь, " +
                "что их ждет очень веселое и увлекательное путешествие", 1)
        val movie3 = RecyclerMoviesItem(R.drawable.pain_and_gain, "Кровью и потом", "Однажды тренеру по фитнесу " +
                "надоело ходить в трениках. Он решил круто изменить свою судьбу и разбогатеть. " +
                "Нашел двух других незадачливых качков и предложил им план похищения своего " +
                "клиента-миллионера. Но если в организме мышц больше, чем мозгов, то даже " +
                "самый лучший план, подсмотренный в экшен-боевике, может не сработать…", 2)
        val movie4 = RecyclerMoviesItem(R.drawable.bronx_tale, "Бронкская история","Гангстер Санни - большой " +
                "человек в квартале Бронкса.Убийство, совершенное Санни, положило начало " +
                "странной дружбе между мафиози и 9 летним подростком Калоджеро. Калоджеро  " +
                "предстоит выбрать: будет ли он зарабатывать уважение тяжким трудом, как отец, " +
                "или пойдет по преступному пути, как Санни...", 3)
        val movie5 = RecyclerMoviesItem(R.drawable.carlitos_way, "Путь Карлито", "Один из главарей наркомафии, " +
                "пуэрториканец Карлито Бриганте, с помощью своего адвоката досрочно выходит на " +
                "свободу. Карлито - уже немолодой, но всё ещё привлекательный и статный мужчина " +
                "решает порвать со своим преступным прошлым и начать новую, более праведную " +
                "жизнь, но возможно ли это...", 4)
        val movie6 = RecyclerMoviesItem(R.drawable.cidad_de_deus, "Город бога", "Город Бога - самый бедный и " +
                "самый криминальный квартал Рио-де-Жанейро, где нет законов, нет властей, а " +
                "правила существования устанавливают банды наркоторговцев. Там рождаются с " +
                "косяком в одной руке и с пушкой - в другой. И для того чтобы выбраться из " +
                "этого ада, нет другого шанса, кроме совершения преступлений.", 5)
        val movie7 = RecyclerMoviesItem(R.drawable.night_school, "Вечерняя школа", "Группе бездельников придется " +
                "пройти обучение в вечерней школе, чтобы получить, наконец, аттестат и найти " +
                "нормальную работу", 6)

        var movies = ArrayList<RecyclerMoviesItem>()
        movies.add(movie1)
        movies.add(movie2)
        movies.add(movie3)
        movies.add(movie4)
        movies.add(movie5)
        movies.add(movie6)
        movies.add(movie7)
    }

    private fun findViewsById(view: View) {
        movieListView =
            view.findViewById<View>(R.id.recyclerView) as ListView
    }

    override fun onItemClick(
        parent: AdapterView<*>, view: View, position: Int,
        id: Long
    ) {
        val movie: RecyclerMoviesItem = parent.getItemAtPosition(position) as RecyclerMoviesItem
        Toast.makeText(activity, movie.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onItemLongClick(
        arg0: AdapterView<*>?, view: View,
        position: Int, arg3: Long
    ): Boolean {
        val button =
            view.findViewById<View>(R.id.imgBtnFavorite) as ImageView
        val tag = button.tag.toString()
        if (tag.equals("border", ignoreCase = true)) {
            sharedPreference!!.addFavorite(activity!!, movies!![position])
            Toast.makeText(activity, activity!!.resources.getString(R.string.add_favr), Toast.LENGTH_SHORT).show()
            button.tag = "red"
            button.setImageResource(R.drawable.ic_favorite_red_24dp)
        } else {
            sharedPreference!!.removeFavorite(activity!!, movies!![position])
            button.tag = "border"
            button.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            Toast.makeText(activity, activity!!.resources.getString(R.string.remove_favr), Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onResume() {
        getActivity()?.setTitle(R.string.app_name)
        getActivity()?.getActionBar()?.setTitle(R.string.app_name)
        super.onResume()
    }

    companion object {
        const val ARG_ITEM_ID = "movie_list"
        const val TAG = "MovieListFragment"
    }

    interface OnMoviesClickListener {
        fun onMoviesClick(item:RecyclerMoviesItem)

    }

}