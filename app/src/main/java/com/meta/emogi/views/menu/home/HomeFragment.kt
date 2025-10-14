package com.meta.emogi.views.menu.home

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.data.network.model.CharacterResponse
import com.meta.emogi.databinding.FragmentHomeBinding
import com.meta.emogi.views.toolbar.ToolbarView
import com.meta.emogi.views.toolbar.ToolbarView.ToolbarRequest


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        // TODO: 툴바 명 설정
        return ToolbarRequest("미정")
    }

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun viewModelClass(): Class<HomeViewModel> { return HomeViewModel::class.java
    }

    override fun registerObservers() {
        viewModel.Go2RankFrag().observe(viewLifecycleOwner) {
            findNavController(requireView()).navigate(R.id.action_homeFragment_to_rankFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MenuListAdapter(makeTemp())

        val glm = GridLayoutManager(requireContext(), 2)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = if (position == 0) 2 else 1
        }

        binding.listRankCharacter.apply {
            layoutManager = glm
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    /// TODO: 임시 더미 데이터
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