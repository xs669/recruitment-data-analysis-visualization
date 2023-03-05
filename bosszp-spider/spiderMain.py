import csv
import json
import os.path
import time

from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By


class spider(object):
    def __init__(self, type, city, page):
        self.type = type  # 搜索关键字
        self.city = city  # 城市
        self.page = page  # 页码
        self.spiderUrl = 'https://www.zhipin.com/web/geek/job?query=%s&city=%s&page=%s'

    def startBrower(self):
        service = Service('chromedriver.exe')
        options = webdriver.ChromeOptions()
        # options.add_experimental_option('excludeSwitches', ['enable-automation'])
        options.add_experimental_option('debuggerAddress', '127.0.0.1:9222')
        brower = webdriver.Chrome(service=service, options=options)
        return brower

    def init(self):
        fileName = './' + self.type + '.csv'
        if not os.path.exists(fileName):
            with open(fileName, 'a+', encoding='utf-8') as wf:
                writer = csv.writer(wf)
                writer.writerow(['title', 'address', 'type', 'educational',
                                 'workExperience', 'workTag', 'salary',
                                 'salaryMonth', 'companyTags', 'hrWork',
                                 'hrName', 'practice', 'companyTitle',
                                 'companyAvatar', 'companyNature', 'companyStatus',
                                 'companyPeople', 'detailUrl', 'companyUrl',
                                 'dist'])

    def save_to_csv(self, rowData):
        fileName = './' + self.type + '.csv'
        with open(fileName, 'a+', newline='', encoding='utf-8') as wf:
            writer = csv.writer(wf)
            writer.writerow(rowData)

    def main(self, page):
        if self.page > page: return
        brower = self.startBrower()
        brower.get(self.spiderUrl % (self.type, self.city, self.page))
        time.sleep(15)
        job_list = brower.find_elements(by=By.XPATH, value='//ul[@class="job-list-box"]/li')
        for index, job in enumerate(job_list):
            try:
                jobData = []

                print('正在爬取的第%d个数据' % (index + 1))
                title = job.find_element(by=By.XPATH, value='.//a[@class="job-card-left"]/div[contains(@class, '
                                                            '"job-title")]/span[@class="job-name"]').text
                # 上海·浦东新区·塘桥
                addresses = job.find_element(by=By.XPATH, value='.//a[@class="job-card-left"]/div[contains(@class, '
                                                                '"job-title")]/span[@class="job-area-wrapper"]/span').text.split(
                    '·')

                address = addresses[0]
                if len(addresses) != 1:
                    dist = addresses[1]
                else:
                    dist = ''

                type = self.type

                tag_list = job.find_elements(by=By.XPATH, value='.//a[@class="job-card-left"]/div[contains(@class, '
                                                                '"job-info")]/ul[@class="tag-list"]/li')
                if len(tag_list) == 2:
                    educational = tag_list[1].text
                    workExperience = tag_list[0].text
                else:
                    educational = tag_list[2].text
                    workExperience = tag_list[1].text

                hrWork = job.find_element(by=By.XPATH, value='.//a[@class="job-card-left"]/div[contains(@class, '
                                                             '"job-info")]/div[@class="info-public"]/em').text

                hrName = job.find_element(by=By.XPATH, value='.//a[@class="job-card-left"]/div[contains(@class, '
                                                             '"job-info")]/div[@class="info-public"]').text[:3]

                workTags = job.find_elements(by=By.XPATH, value='./div[contains(@class, "job-card-footer")]/ul['
                                                                '@class="tag-list"]/li')
                workTag = json.dumps(list(map(lambda x: x.text, workTags)), ensure_ascii=False)

                practice = 0

                salaries = job.find_element(by=By.XPATH, value='.//a[@class="job-card-left"]/div[contains(@class, '
                                                               '"job-info")]/span[@class="salary"]').text
                if salaries.find('K') != -1:
                    salaries = salaries.split('·')
                    if len(salaries) == 1:
                        salary = list(map(lambda x: int(x) * 1000, salaries[0].replace('K', '').split('-')))
                        salaryMonth = '0薪'
                    else:
                        salary = list(map(lambda x: int(x) * 1000, salaries[0].replace('K', '').split('-')))
                        salaryMonth = salaries[1]
                else:
                    salary = list(map(lambda x: int(x), salaries.replace('元/天', '').split('-')))
                    salaryMonth = '0薪'
                    practice = 1

                companyTitle = job.find_element(by=By.XPATH, value='.//div[@class="job-card-right"]/div['
                                                                   '@class="company-info"]/h3/a').text

                companyAvatar = job.find_element(by=By.XPATH, value='.//div[@class="job-card-right"]/div['
                                                                    '@class="company-logo"]/a/img').get_attribute('src')

                companyInfos = job.find_elements(by=By.XPATH, value='.//div[@class="job-card-right"]/div['
                                                                    '@class="company-info"]/ul['
                                                                    '@class="company-tag-list"]/li')
                if len(companyInfos) == 3:
                    companyNature = companyInfos[0].text
                    companyStatus = companyInfos[1].text
                    companyPeoples = companyInfos[2].text
                    if companyPeoples != '10000人以上':
                        companyPeople = list(map(lambda x: int(x), companyPeoples.replace('人', '').split('-')))
                    else:
                        companyPeople = [0, 10000]
                else:
                    companyNature = companyInfos[0].text
                    companyStatus = '未融资'
                    companyPeoples = companyInfos[1].text
                    if companyPeoples != '10000人以上':
                        companyPeople = list(map(lambda x: int(x), companyPeoples.replace('人', '').split('-')))
                    else:
                        companyPeople = [0, 10000]

                companyTags = job.find_element(by=By.XPATH, value='./div[contains(@class, "job-card-footer")]/div['
                                                                  '@class="info-desc"]').text
                if not companyTags:
                    companyTags = '无'
                else:
                    companyTags = json.dumps(companyTags.split('，'), ensure_ascii=False)

                detailUrl = job.find_element(by=By.XPATH, value='.//a[@class="job-card-left"]').get_attribute('href')

                companyUrl = job.find_element(by=By.XPATH, value='.//div[@class="job-card-right"]/div['
                                                                 '@class="company-info"]/h3/a').get_attribute('href')
                jobData.append(title)
                jobData.append(address)
                jobData.append(type)
                jobData.append(educational)
                jobData.append(workExperience)
                jobData.append(workTag)
                jobData.append(salary)
                jobData.append(salaryMonth)
                jobData.append(companyTags)
                jobData.append(hrWork)
                jobData.append(hrName)
                jobData.append(practice)
                jobData.append(companyTitle)
                jobData.append(companyAvatar)
                jobData.append(companyNature)
                jobData.append(companyStatus)
                jobData.append(companyPeople)
                jobData.append(detailUrl)
                jobData.append(companyUrl)
                jobData.append(dist)

                self.save_to_csv(jobData)

            except:
                pass

        self.page += 1
        self.main(page)


if __name__ == '__main__':
    # 101010100 北京
    # 101020100 上海
    # 101280100 广州
    # 101280600 深圳
    # 101210100 杭州
    # 101030100 天津
    # 101110100 西安
    # 101190400 苏州
    # 101200100 武汉
    # 101230200 厦门
    # 101250100 长沙
    # 101270100 成都
    # 101180100 郑州
    # 101040100 重庆
    # 101240100 南昌
    cities = ['101010100', '101020100', '101280100', '101280600',
              '101210100', '101030100', '101110100', '101190400',
              '101200100', '101230200', '101250100', '101270100',
              '101180100', '101040100', '101240100']
    for city in cities:
        spiderObj = spider('文案', city, 1)
        spiderObj.init()
        spiderObj.main(10)
        time.sleep(15)
