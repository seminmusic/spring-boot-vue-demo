function isEmptyObject(o) {
    return (Object.keys(o).length === 0 && o.constructor === Object);
}

export const UtilityHelper = {
    isEmptyObject
};
