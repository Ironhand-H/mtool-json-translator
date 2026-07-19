export async function chooseFile() {

    const response = await fetch("/api/chooseFile", {
        method: "POST"
    });

    return await response.json();
}

export async function projectExist() {

    const response = await fetch("/api/projectExist", {
        method: "POST"
    });

    return await response.json();
}

export async function createProject(fileDir) {

    const response = await fetch("/api/createProject", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({"fileDir": fileDir})
    });

    return await response.json();
}