import { useState, useEffect } from 'react'
import axios from 'axios'
import Navbar from '../components/Navbar'

function Applications() {
  const [activeTab, setActiveTab] = useState('applications')
  const [applications, setApplications] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [editingApp, setEditingApp] = useState(null)

  const API_URL = import.meta.env.VITE_API_URL
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')

  const config = { headers: { Authorization: `Bearer ${token}` } }

  useEffect(() => {
    fetchApplications()
  }, [])

  const fetchApplications = async () => {
    try {
      const response = await axios.get(`${API_URL}/applications/applications/${userId}`, config)
      setApplications(response.data)
    } catch (error) {
      console.error(error)
    }
  }

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/applications/applications/delete/${id}`, config)
      fetchApplications()
    } catch (error) {
      console.error(error)
    }
  }

  const handleSave = async () => {
  try {
    if (editingApp?.id) {
      await axios.put(`${API_URL}/applications/applications/update/${editingApp.id}`, editingApp, config)
    } else {
      await axios.post(`${API_URL}/applications/applications/create`, {
        ...editingApp,
        userId: userId
      }, config)
    }
    setShowModal(false)
    fetchApplications()
  } catch (error) {
    console.error(error)
  }
}

  return (
    <div className="min-h-screen bg-gradient-to-br from-[#F6EECC] to-[#E6D789]">
      <Navbar />

      {/* Tabs */}
      <div className="flex gap-4 px-8 pt-6">
        <button
          onClick={() => setActiveTab('applications')}
          className={`px-6 py-2 rounded-lg font-semibold ${activeTab === 'applications' ? 'bg-amber-500 text-white' : 'bg-white text-amber-500'}`}>
          Applications
        </button>
        <button
          onClick={() => setActiveTab('ai')}
          className={`px-6 py-2 rounded-lg font-semibold ${activeTab === 'ai' ? 'bg-amber-500 text-white' : 'bg-white text-amber-500'}`}>
          AI Resume
        </button>
        <button
          onClick={() => setActiveTab('reminders')}
          className={`px-6 py-2 rounded-lg font-semibold ${activeTab === 'reminders' ? 'bg-amber-500 text-white' : 'bg-white text-amber-500'}`}>
          Reminders
        </button>
      </div>

      {/* Tab Content */}
      <div className="p-8">
        {activeTab === 'applications' && (
  <div className="bg-white rounded-xl p-6">
    <div className="flex justify-between items-center mb-4">
      <h2 className="font-[Manrope] font-extrabold text-xl text-amber-500">My Applications</h2>
      <button
        onClick={() => { setEditingApp(null); setShowModal(true) }}
        className="bg-amber-500 text-white px-4 py-2 rounded-lg font-semibold hover:text-sky-300">
        + Add Application
      </button>
    </div>

    <table className="w-full text-left">
      <thead>
        <tr className="border-b border-gray-200">
          <th className="py-2 text-amber-500">Job Name</th>
          <th className="py-2 text-amber-500">Role</th>
          <th className="py-2 text-amber-500">Status</th>
          <th className="py-2 text-amber-500">Date Applied</th>
          <th className="py-2 text-amber-500">Actions</th>
        </tr>
      </thead>
      <tbody>
        {applications.map(app => (
          <tr key={app.id} className="border-b border-gray-100">
            <td className="py-3 text-sky-300">{app.jobName}</td>
            <td className="py-3 text-sky-300">{app.jobRole}</td>
            <td className="py-3 text-sky-300">{app.jobStatus}</td>
            <td className="py-3 text-sky-300">{app.dateApplied}</td>
            <td className="py-3 flex gap-2">
              <button
                onClick={() => { setEditingApp(app); setShowModal(true) }}
                className="bg-sky-300 text-white px-3 py-1 rounded-lg text-sm hover:bg-sky-400">
                Edit
              </button>
              <button
                onClick={() => handleDelete(app.id)}
                className="bg-red-400 text-white px-3 py-1 rounded-lg text-sm hover:bg-red-500">
                Delete
              </button>
            </td>
          </tr>
        ))}

        {applications.length === 0 && (
    <tr>
      <td colSpan="5" className="py-6 text-center text-gray-400">No applications yet</td>
    </tr>
  )}
      </tbody>
    </table>
    {showModal && (
  <div className="fixed inset-0 bg-gradient-to-br from-[#F6EECC] to-[#E6D789] flex justify-center items-center">
    <div className="bg-white rounded-xl p-8 w-[500px] flex flex-col gap-4">
      <h2 className="font-[Manrope] font-extrabold text-xl text-amber-500">
        {editingApp ? 'Edit Application' : 'Add Application'}
      </h2>

      <input placeholder="Job Name" defaultValue={editingApp?.jobName || ''}
        onChange={e => setEditingApp({...editingApp, jobName: e.target.value})}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full" />

      <input placeholder="Job Role" defaultValue={editingApp?.jobRole || ''}
        onChange={e => setEditingApp({...editingApp, jobRole: e.target.value})}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full" />

      <input placeholder="Job URL" defaultValue={editingApp?.jobUrl || ''}
        onChange={e => setEditingApp({...editingApp, jobUrl: e.target.value})}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full" />

      <select defaultValue={editingApp?.jobStatus || ''}
        onChange={e => setEditingApp({...editingApp, jobStatus: e.target.value})}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full">
        <option value="">Select Status</option>
        <option value="Applied">Applied</option>
        <option value="Waiting Response">Waiting Response</option>
        <option value="Interview">Interview</option>
        <option value="Denied">Denied</option>
        <option value="Offer">Offer</option>
      </select>

      <input type="date" defaultValue={editingApp?.dateApplied || ''}
        onChange={e => setEditingApp({...editingApp, dateApplied: e.target.value})}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full" />

      <textarea placeholder="Notes" defaultValue={editingApp?.notes || ''}
        onChange={e => setEditingApp({...editingApp, notes: e.target.value})}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full" />

      <div className="flex gap-4">
        <button onClick={handleSave}
          className="bg-amber-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-amber-600 w-full">
          {editingApp?.id ? 'Update' : 'Create'}
        </button>
        <button onClick={() => setShowModal(false)}
          className="bg-gray-200 text-gray-700 px-6 py-2 rounded-lg font-semibold hover:bg-gray-300 w-full">
          Cancel
        </button>
      </div>
    </div>
  </div>
)}
  </div>
)}
        {activeTab === 'ai' && <div>AI Resume content</div>}
        {activeTab === 'reminders' && <div>Reminders content</div>}
      </div>

    </div>
  )
}

export default Applications