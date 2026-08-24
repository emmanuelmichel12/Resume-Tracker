import {useState, useEffect} from 'react'
import Navbar from '../components/Navbar'
import axios from 'axios'


function Dashboard() {
  const firstName = localStorage.getItem('firstName')
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')
  const APPLICATION_URL = import.meta.env.VITE_APPLICATION_URL
  const [applications, setApplications] = useState([])
  
  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const response = await axios.get(`${APPLICATION_URL}/applications/${userId}`, {
          headers: { Authorization: `Bearer ${token}` }
        })
        setApplications(response.data)
      } catch (error) {
        console.error('Error fetching applications:', error)
      }
    }
    fetchApplications()
  }, [])

  const numApplied = applications.length
  const numInterviews = applications.filter(app => app.status === 'Interview').length
  const numOffers = applications.filter(app => app.status === 'Offer').length
  const numDenied = applications.filter(app => app.status === 'Denied').length
  const numWaitingResponse = applications.filter(app => app.status === 'Waiting Response').length



  return (
    
    <div className="min-h-screen bg-gradient-to-br from-[#F6EECC] to-[#E6D789]">
      <Navbar />

      <div className="p-8 flex flex-col gap-6">

        {/* Welcome */}
        <h1 className="font-[Manrope] font-extrabold text-amber-500 text-3xl">
          Welcome back, {firstName}!
        </h1>

        {/* Applied - full width */}
        <div className="bg-white rounded-xl p-6 flex flex-col items-center w-full">
          <p className="text-amber-500 font-semibold">Applied</p>
          <p className="text-4xl font-extrabold text-sky-300">{numApplied}</p>
        </div>

        {/* Stats - 2 per row */}
        <div className="grid grid-cols-2 gap-6">
          <div className="bg-white rounded-xl p-6 flex flex-col items-center">
            <p className="text-amber-500 font-semibold">Waiting Response</p>
            <p className="text-4xl font-extrabold text-sky-300">{numWaitingResponse}</p>
          </div>
          <div className="bg-white rounded-xl p-6 flex flex-col items-center">
            <p className="text-amber-500 font-semibold">Interview</p>
            <p className="text-4xl font-extrabold text-sky-300">{numInterviews}</p>
          </div>
          <div className="bg-white rounded-xl p-6 flex flex-col items-center">
            <p className="text-amber-500 font-semibold">Denied</p>
            <p className="text-4xl font-extrabold text-sky-300">{numDenied}</p>
          </div>
          <div className="bg-white rounded-xl p-6 flex flex-col items-center">
            <p className="text-amber-500 font-semibold">Offer</p>
            <p className="text-4xl font-extrabold text-sky-300">{numOffers}</p>
          </div>
        </div>

        {/* Upcoming Reminders */}
        <div className="bg-white rounded-xl p-6">
          <h2 className="font-[Manrope] font-extrabold text-xl text-amber-500 mb-4">Upcoming Reminders</h2>
          <ul className="flex flex-col gap-2 text-sky-300">
            <li>No upcoming reminders</li>
          </ul>
        </div>

      </div>
    </div>
  )
}

export default Dashboard