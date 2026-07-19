import { useState } from 'react'
import HomePage from './pages/HomePage.jsx'
import Translate from "./pages/Translate.jsx";
import {chooseFile} from "./API/API.js";

function App() {
    const [currentPage, setCurrentPage] = useState("home");
    const [selectedFileDir, setFileDir] = useState("");

    switch(currentPage){
        case "translate":
            return <Translate changePage = {changePage}/>
        case "home":
            return <HomePage
                changePage = {changePage}

                selectedFileDir = {selectedFileDir}
                handleChooseFile = {handleChooseFile}
            />;
        default:
            return <HomePage
                changePage = {changePage}

                selectedFileDir = {selectedFileDir}
                handleChooseFile = {handleChooseFile}
            />;
    }

    function changePage(page) {
        setCurrentPage(page)
    }

    async function handleChooseFile() {
        const result = await chooseFile();

        //console.log(result);

        if(result.success){
            setFileDir(result.data.selectedFileDir);
        }
    }
}

export default App
