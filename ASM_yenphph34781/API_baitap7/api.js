const express = require('express');
const router = express.Router();
module.exports = router;
require('dotenv').config();
router.get('/', (req, res) => {
    res.send('Vao api mobile');
});
const mongoose = require('mongoose');
const clothesModel = require('./Model/clothes');
router.get('/clothes/', async (req, res) => {
    await mongoose.connect((process.env.MONGODB));
    let clothes = await clothesModel.find();
    console.log(clothes);
    res.json(clothes);
})
router.post('/clothes/', async (req, res) => {
    await mongoose.connect((process.env.MONGODB));
    const newclothes = new clothesModel(req.body);
    const saveFruit = await newclothes.save();
    res.json({ saveFruit })
})
router.delete('/clothes/:id', async function (req, res, next) {
    try {
        const result = await clothesModel.findByIdAndDelete(req.params.id, req.body);
        if (!result) {
            res.status(404).json({
                message: "Service not found"
            });
        } else {
            res.status(200).json({
                message: "Service updated successfully",
                data: result
            });
        }

    } catch (error) {
        res.status(500).json(error);
    }
});
router.put('/clothes/:id', async function (req, res, next) {
    try {
        const result = await clothesModel.findByIdAndUpdate(req.params.id, req.body);
        if (!result) {
            res.status(404).json({
                message: "Service not found"
            });
        } else {
            res.status(200).json({
                message: "Service update successfuly",
                data: result
            });
        }
    } catch (error) {
        res.status(500).json(error);
    }
    // =======
    
})