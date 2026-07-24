import React, { useState } from 'react';
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

function Login() {

  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const API_URL = import.meta.env.VITE_API_URL
  const navigate = useNavigate()

  const handleLogin = async () => {
    try {
      const data = {
        username: username,
        password: password
      }
      const response = await axios.post(`${API_URL}/auth/api/auth/login`, data)
      console.log(response.data)
    } catch (error) {
      console.error('Error logging in user:', error)
      }
    }

  return (
        <div className= "flex h-screen bg-gradient-to-br from-[#F6E*CC] to-[#E6D789]">

        <div className="w-15/16 aspect-video">
        <img src="/pic.jpeg" alt="Job Application Tracker" className="w-full h-full object-cover" />
        </div>

        <div className="flex flex-col justify-between items-center w-1/3 pb-12 pt-12">

        <div>
        <h1 className="mb-4 font-[Manrope] font-extrabold tracking-tight text-5xl flex flex-col justify-center items-center">
        <span className="text-amber-500 mb-2">Every application.</span>
        <span className="text-amber-500 mb-2">Every opportunity.</span>
        <span className="text-sky-300 mb-2">One path forward.</span>
        </h1>
        </div>

        <div>

        <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="mb-4 px-4 py-2 border rounded-lg w-full border-amber-500 border-4"
        />
        
        <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="mb-4 px-4 py-2 border rounded-lg w-full border-amber-500 border-4"
        />
        </div>
        
        <div className="flex flex-col gap-4 w-full px-8">
        <button className = "bg-amber-500 px-9 py-3 rounded-lg font-semibold hover:bg-sky-300" onClick={handleLogin}>Login</button>
        </div>

        </div>

        </div>
  )
}
export default Login