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