import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaTrash, FaPlus } from "react-icons/fa";
import axios from "axios";
import "../../CSS/storage-page.css"; //Import del file CSS
import IngredientCard from "../IngredientCard.tsx";


interface Ingredient {
    ingredientId: number;
    quantity: number;
    expirationDate: string;
    name: string;
}


interface IngredientDTO {
    ingredientId: number;
    quantity: number;
    username: string;
    expirationDate: string;
}



const StoragePage = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const token = localStorage.getItem('authToken');
    const navigate = useNavigate();


    const fetchStorage = async () => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:3005/api/storage`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            }).then((response) => {

                const dtos: IngredientDTO[] = response.data;

                const ingredientList = dtos.map(dto => ({
                    ingredientId: dto.ingredientId,
                    quantity: dto.quantity,
                    expirationDate: dto.expirationDate,
                    name: "",
                  }));
                setIngredients(ingredientList);


            console.log("storage fetched. Response:");
            console.log(response.data);
            fetchAllIngredients();
            console.log("Ingredients fetched");
        }); 
        } catch (err) {
            console.log(err.message)
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const fetchIngredientDetails = async (ingredientId: number) => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            const response = await axios.get(`http://localhost:3004/recipes-ingredients/ingredients/${ingredientId}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            return response.data;
        } catch (err) {
            setError('Error fetching ingredient details');
            return null;
        }
    };



    const fetchAllIngredients = async () => {
        if (ingredients.length !== 0) {
            const ingredientDetails = await Promise.all(
                ingredients.map(async (ingredient) => {
                    const ingredientDetails = await fetchIngredientDetails(ingredient.ingredientId);
                    if (ingredientDetails) {
                        return {
                            ...ingredient,
                            name: ingredientDetails.name,
                        };
                    }
                    return ingredient;
                })
            );
            setIngredients(ingredientDetails);
        }
    };

    const handleAddIngredientClick= () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleAddIngredientToList = () => {
        fetchStorage();
        setIsModalOpen(false);
    };

    useEffect(() => {
        fetchStorage();
        console.log("ingredients", ingredients);
        }, [token]);







}

export default StoragePage;