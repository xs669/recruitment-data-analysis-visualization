import request from "@/utils/request";

export function getJobTypeCount() {
    return request({
        url: '/getJobTypeCount',
        method: 'GET'
    })
}

export function getAvgSalaryByJobType() {
    return request({
        url: '/getAvgSalaryByJobType',
        method: 'GET'
    })
}

export function getWorkExperienceByGroup() {
    return request({
        url: '/getWorkExperienceByGroup',
        method: 'GET'
    })
}

export function getEducationalByGroup() {
    return request({
        url: '/getEducationalByGroup',
        method: 'GET'
    })
}

export function getMainDataCount() {
    return request({
        url: '/getMainDataCount',
        method: 'GET'
    })
}

export function getSearchParameter() {
    return request({
        url: '/getSearchParameter',
        method: 'GET'
    })
}

export function getAllJobData(searchParameterVo) {
    return request({
        url: '/getAllJobData',
        method: 'POST',
        data: {
            ...searchParameterVo
        }
    })
}

export function getWorkExperienceAndAvgSalaryByJobType(jobType) {
    return request({
        url: '/getWorkExperienceAndAvgSalaryByJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getEducationAndAvgSalaryByJobType(jobType) {
    return request({
        url: '/getEducationAndAvgSalaryByJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getJobTypeAndHandledJobType() {
    return request({
        url: '/getJobTypeAndHandledJobType',
        method: 'GET'
    })
}

export function getWorkTagByType(jobType) {
    return request({
        url: '/getWorkTagByType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getEducationalByGroupAndJobType(jobType) {
    return request({
        url: '/getEducationalByGroupAndJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getWorkExperienceByGroupAndJobType(jobType) {
    return request({
        url: '/getWorkExperienceByGroupAndJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getCompanyNatureByGroupAndJobType(jobType) {
    return request({
        url: '/getCompanyNatureByGroupAndJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getCompanyPeopleByGroupAndJobType(jobType) {
    return request({
        url: '/getCompanyPeopleByGroupAndJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getCompanyNatureAndAvgSalaryByJobType(jobType) {
    return request({
        url: '/getCompanyNatureAndAvgSalaryByJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getCompanyPeopleAndAvgSalaryByJobType(jobType) {
    return request({
        url: '/getCompanyPeopleAndAvgSalaryByJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getCompanyTagsByJobType(jobType) {
    return request({
        url: '/getCompanyTagsByJobType',
        method: 'POST',
        data: {
            ...jobType
        }
    })
}

export function getAddressByGroupAndJobType(jobTypeAndAddressVo) {
    return request({
        url: '/getAddressByGroupAndJobType',
        method: 'POST',
        data: {
            ...jobTypeAndAddressVo
        }
    })
}

export function getAllAddress() {
    return request({
        url: '/getAllAddress',
        method: 'GET'
    })
}

export function login(loginForm) {
    return request({
        url: '/login',
        method: 'POST',
        data: {
            ...loginForm
        }
    })
}

export function logout(admin) {
    return request({
        url: '/exit',
        method: 'POST',
        data: {
            ...admin
        }
    })
}

export function updateAdminDetail(admin) {
    return request({
        url: '/updateAdminDetail',
        method: 'POST',
        data: {
            ...admin
        }
    })
}

export function getAdminDetail(admin) {
    return request({
        url: '/getAdminDetail',
        method: 'POST',
        data: {
            ...admin
        }
    })
}

export function changePassword(password) {
    return request({
        url: '/changePassword',
        method: 'POST',
        data: {
            ...password
        }
    })
}
