import { useState, useEffect } from 'react'
import axios from 'axios'
import Navbar from '../components/Navbar'

function Applications() {
  const [activeTab, setActiveTab] = useState('applications')
  const [applications, setApplications] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [editingApp, setEditingApp] = useState(null)

  const [resumeFile, setResumeFile] = useState(null)
  const [uploadSuccess, setUploadSuccess] = useState(false)
  const [selectedAppId, setSelectedAppId] = useState('')
  const [jobDescription, setJobDescription] = useState('')
  const [aiOutput, setAiOutput] = useState('')

  const [reminderAppId, setReminderAppId] = useState('')
  const [notificationType, setNotificationType] = useState('')
  const [reminderMessage, setReminderMessage] = useState('')
  const [scheduledFor, setScheduledFor] = useState('')
  const [reminderSuccess, setReminderSuccess] = useState(false)

  const APPLICATION_URL = import.meta.env.VITE_APPLICATION_URL
  const AI_URL = import.meta.env.VITE_AI_URL
  const NOTIFICATION_URL = import.meta.env.VITE_NOTIFICATION_URL
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')

  const config = { headers: { Authorization: `Bearer ${token}` } }

  useEffect(() => {
    fetchApplications()
  }, [])

  const fetchApplications = async () => {
    try {
      const response = await axios.get(`${APPLICATION_URL}/applications/${userId}`, config)
      setApplications(response.data)
    } catch (error) {
      console.error(error)
    }
  }

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${APPLICATION_URL}/applications/delete/${id}`, config)
      fetchApplications()
    } catch (error) {
      console.error(error)
    }
  }

  const handleSave = async () => {
  try {
    if (editingApp?.id) {
      await axios.put(`${APPLICATION_URL}/applications/update/${editingApp.id}`, editingApp, config)
    } else {
      await axios.post(`${APPLICATION_URL}/applications/create`, {
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

const handleUpload = async () => {
  try {
    const formData = new FormData()
    formData.append('file', resumeFile)
    formData.append('userId', userId)
    await axios.post(`${AI_URL}/resume/upload`, formData, {
      headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'multipart/form-data' }
    })
    setUploadSuccess(true)
  } catch (error) {
    console.error(error)
  }
}

const handleTailor = async () => {
  try {
    const response = await axios.post(`${AI_URL}/resume/ai-tailoring`, {
      id: userId,
      applicationId: selectedAppId,
      jobDescription: jobDescription
    }, config)
    setAiOutput(response.data.aiOutput)
  } catch (error) {
    console.error(error)
  }
}

const handleSendReminder = async () => {
  try {
    await axios.post(`${NOTIFICATION_URL}/notifications/send`, {
      userId: userId,
      applicationId: reminderAppId,
      notificationType: notificationType,
      message: reminderMessage,
      scheduledFor: scheduledFor ? new Date(scheduledFor).toISOString().slice(0, 19) : null
    }, config)
    setReminderSuccess(true)
    setTimeout(() => setReminderSuccess(false), 4000)
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
    {activeTab === 'ai' && (
  <div className="flex flex-col gap-6">

    {/* Upload Resume */}
    <div className="bg-white rounded-xl p-6">
      <h2 className="font-[Manrope] font-extrabold text-xl text-amber-500 mb-4">Upload Resume</h2>
      <div className="flex gap-4 items-center">
        <input
          type="file"
          accept=".pdf"
          onChange={e => setResumeFile(e.target.files[0])}
          className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full"
        />
        <button
          onClick={handleUpload}
          className="bg-amber-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-amber-600 whitespace-nowrap">
          Upload
        </button>
      </div>
      {uploadSuccess && <p className="text-sky-300 mt-2">Resume uploaded successfully!</p>}
    </div>

    {/* AI Tailoring */}
    <div className="bg-white rounded-xl p-6">
      <h2 className="font-[Manrope] font-extrabold text-xl text-amber-500 mb-4">AI Resume Tailoring</h2>
      <div className="flex flex-col gap-4">
        <select
          onChange={e => setSelectedAppId(e.target.value)}
          className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full">
          <option value="">Select Application</option>
          {applications.map(app => (
            <option key={app.id} value={app.id}>{app.jobName} — {app.jobRole}</option>
          ))}
        </select>
        <textarea
          placeholder="Paste the job description here..."
          onChange={e => setJobDescription(e.target.value)}
          className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full h-32"
        />
        <button
          onClick={handleTailor}
          className="bg-amber-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-amber-600">
          Generate Bullet Points
        </button>
      </div>
      {aiOutput && (
        <div className="mt-6">
          <h3 className="font-[Manrope] font-extrabold text-amber-500 mb-2">AI Output</h3>
          <p className="text-sky-300 whitespace-pre-line">{aiOutput}</p>
        </div>
      )}
    </div>

  </div>
)}
        {activeTab === 'reminders' && (
  <div className="bg-white rounded-xl p-6">
    <h2 className="font-[Manrope] font-extrabold text-xl text-amber-500 mb-6">Send a Reminder</h2>
    <div className="flex flex-col gap-4">

      <select
        value={reminderAppId}
        onChange={e => setReminderAppId(e.target.value)}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full">
        <option value="">Select Application</option>
        {applications.map(app => (
          <option key={app.id} value={app.id}>{app.jobName} — {app.jobRole}</option>
        ))}
      </select>

      <select
        value={notificationType}
        onChange={e => setNotificationType(e.target.value)}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full">
        <option value="">Select Reminder Type</option>
        <option value="FOLLOW_UP">Follow Up</option>
        <option value="INTERVIEW">Interview</option>
        <option value="DEADLINE">Deadline</option>
      </select>

      <textarea
        placeholder="Message..."
        value={reminderMessage}
        onChange={e => setReminderMessage(e.target.value)}
        className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full h-28"
      />

      <div className="flex flex-col gap-1">
        <label className="text-amber-500 font-semibold text-sm">Scheduled For</label>
        <input
          type="datetime-local"
          value={scheduledFor}
          onChange={e => setScheduledFor(e.target.value)}
          className="border-2 border-amber-500 px-4 py-2 rounded-lg w-full"
        />
      </div>

      <button
        onClick={handleSendReminder}
        className="bg-amber-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-amber-600">
        Send Reminder
      </button>

      {reminderSuccess && (
        <p className="text-sky-300 font-semibold">Reminder sent to your email!</p>
      )}
    </div>
  </div>
)}
      </div>
    </div>
  )
}


export default Applications