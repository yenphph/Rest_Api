const mongoose = require('mongoose');
const local ="mongodb://127.0.0.1:27017/asmPHPH34781";
const connect = async () => {
    try {
        await mongoose.connect(local);
        console.log('Connect success');
    } catch (error) {
        console.log('Connection to MongoDB false: ', error);
    }
}
module.exports = {connect}