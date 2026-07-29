import { useNavigate } from 'react-router-dom'

function Navbar() {
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem('token')
    navigate('/')
  }

  return (
    <nav className="flex justify-between items-center px-8 py-4 bg-amber-500">

        <h1 className="font-[Manrope] font-extrabold text-white text-xl">
  appl<span className="text-sky-300">AI</span>path
</h1>

      <div className="flex gap-8">
        <button onClick={() => navigate('/dashboard')} className="text-white font-semibold hover:text-sky-300">Dashboard</button>
        <button onClick={() => navigate('/applications')} className="text-white font-semibold hover:text-sky-300">Applications</button>
        <button onClick={handleLogout} className="text-white font-semibold hover:text-sky-300">Logout</button>
      </div>
    </nav>
  )
}

export default Navbar

//bg-sky-300