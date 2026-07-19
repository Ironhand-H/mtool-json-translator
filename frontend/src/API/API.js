export async function chooseFile() {

    const response = await fetch("/api/chooseFile", {
        method: "POST"
    });

    return await response.json();
}