package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeTodayMusic01AlbumIv.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
//        }

//        데이터 리스트 생성 더미 데이터
//        albumDatas.apply {
//            add(Album("Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
//            add(Album("LILAC", "아이유(IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파(aespa)", R.drawable.img_album_exp3))
//            add(Album("작은 것들을 위한 시 (Boy With Love) (Feat. Halsey)", "방탄소년단(BTS)", R.drawable.img_album_exp4))
//            add(Album("뿜뿜", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연", R.drawable.img_album_exp6))
//        }
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicRv.adapter = albumRVAdapter
        binding.homeTodayMusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            private fun changeAlbumFragment(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    }).commitAllowingStateLoss()
            }
        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}