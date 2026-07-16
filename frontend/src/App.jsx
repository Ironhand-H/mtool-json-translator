import { useState } from 'react'
import HomePage from './pages/HomePage.jsx'
import Translate from "./pages/Translate.jsx";
import './App.css'

function App() {
    const [currentPage, setCurrentPage] = useState("home")

    switch(currentPage){
        case "translate":
            return <Translate />
        case "home":
            return <HomePage changePage = {changePage} />;
        default:
            return <HomePage changePage = {changePage} />;
    }

    function changePage(page) {
        setCurrentPage(page)
    }
}

export default App
