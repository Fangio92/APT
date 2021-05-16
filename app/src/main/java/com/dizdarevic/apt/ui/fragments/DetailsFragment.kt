package com.dizdarevic.apt.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dizdarevic.apt.MainActivity
import com.dizdarevic.apt.repository.MainRepository
import com.dizdarevic.apt.repository.RetrofitService
import com.dizdarevic.apt.databinding.FragmentDetailsBinding
import com.dizdarevic.apt.models.RandomUser
import com.dizdarevic.apt.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)?.setSupportActionBar(binding?.toolbar)

        val repository = MainRepository(RetrofitService.getInstance())

        val map = HashMap<String, String>()

        map.put("seed", getRandomString())

        val response = repository.getUsers(map)
        response.enqueue(object : Callback<RandomUser> {
            override fun onResponse(call: Call<RandomUser>, response: Response<RandomUser>) {
                val user = response.body()!!.userList[0]
                setData(user)
            }

            override fun onFailure(call: Call<RandomUser>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData(user: User) {
        Glide.with(requireContext()).load(user.picture.large).into(binding!!.profileImage)

        binding?.username?.text=user.login.username
        binding?.email?.text=user.email
        binding?.country?.text="Age: ${user.dob.age}"
        binding?.name?.text=user.getName()
        binding?.adress?.text=user.location.getAdress()
        binding?.phone1?.text=user.cell
        binding?.phone2?.text=user.phone

        binding?.adress?.setOnClickListener {
            Toast.makeText(requireContext(), "Kliknuli ste na adresu", Toast.LENGTH_SHORT).show()
        }

        binding?.phone1?.setOnClickListener {
            showAlertDialog(user.cell)
        }

        binding?.phone2?.setOnClickListener {
            showAlertDialog(user.phone)
        }
    }

    private fun showAlertDialog(phone: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle("Call")

        alertDialogBuilder
            .setMessage("Are you sure you want to call ${phone}")
            .setCancelable(true)
            .setPositiveButton("Call") { dialog, id ->
            }
            .setNegativeButton("No") { dialog, id ->

                dialog.cancel()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getRandomString(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString = (1..5)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return randomString
    }
}