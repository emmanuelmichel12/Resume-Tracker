import { useNavigate } from 'react-router-dom'

function Home() {
  const navigate = useNavigate()

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

      <div className="flex flex-col gap-4 w-full px-8">
      <button className = "bg-amber-500 px-9 py-3 rounded-lg font-semibold hover:bg-sky-300"onClick={() => navigate('/login')}>Login</button>
      <button className = "bg-amber-500 px-9 py-3 rounded-lg font-semibold hover:bg-sky-300" onClick={() => navigate('/register')}>Sign Up!</button>
      </div>

      </div>
    </div>
  )
}

export default Home