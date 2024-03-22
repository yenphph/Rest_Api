const mongoose = require('mongoose');
const ClothesSchema = new mongoose.Schema({
    type:{
        type: String,
    },
    name:{
        type: String,
    },
    price:{
        type: Number,
    },
    // image:{
    //     type: Array,
    // }
}, {
    timestamps: true
});
module.exports = mongoose.model('clothes', ClothesSchema);