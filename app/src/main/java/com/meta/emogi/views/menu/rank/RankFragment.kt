package com.meta.emogi.views.menu.rank

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.data.network.model.CharacterResponse
import com.meta.emogi.databinding.FragmentHomeBinding
import com.meta.emogi.databinding.FragmentRankBinding
import com.meta.emogi.views.menu.home.HomeViewModel
import com.meta.emogi.views.menu.home.MenuListAdapter
import com.meta.emogi.views.toolbar.ToolbarView
import com.meta.emogi.views.toolbar.ToolbarView.ToolbarRequest

class RankFragment : BaseFragment<FragmentRankBinding, RankViewModel>() {

    companion object {
        fun newInstance() = RankFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        // TODO: 툴바 명 설정
        return ToolbarRequest("미정")
    }

    override fun layoutId(): Int {
        return R.layout.fragment_rank
    }

    override fun viewModelClass(): Class<RankViewModel> {
        return RankViewModel::class.java
    }


    override fun registerObservers() {
        viewModel.Go2HomeFrag().observe(viewLifecycleOwner) {
            findNavController(requireView()).navigate(R.id.action_rankFragment_to_homeFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MenuRankListAdapter(makeTemp())

        binding.listRankCharacter.apply {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }


    fun makeTemp() : MutableList<CharacterResponse> {
        val mockList = mutableListOf<CharacterResponse>()

        val names = listOf("Eve", "Milo", "Luna", "Max", "Nari", "Leo")
        val details = listOf("Energetic AI", "Curious bot", "Dreamy artist", "Tech explorer", "Smart friend", "Calm thinker")

        for (i in names.indices) {
            val c = CharacterResponse(names[i], details[i])
            c.setCharacterId(i + 1)
            c.setCharacterProfile("https://picsum.photos/300/300?random=${i + 1}") // 랜덤 이미지
            c.setCharacterGender(if (i % 2 == 0) "female" else "male")
            c.setCharacterPersonality(listOf("Friendly", "Cool", "Funny", "Kind", "Smart", "Calm")[i % 6])
            c.setCharacterDescription("Short bio ${i + 1}")
            c.setCharacterGreeting("Hello from ${names[i]}")
            c.setCharacterIsPublic(true)
            mockList.add(c)
        }
        return mockList
    }
}