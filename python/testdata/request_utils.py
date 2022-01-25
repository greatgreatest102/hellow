import requests
import json
import os
from requests.auth import HTTPBasicAuth


class RequestActions:

    def __init__(self):
        self.auth = HTTPBasicAuth(os.getenv('application_token'), os.getenv('admin_access_token'))
        self.headers = {
            "Content-Type": "application/json"
        }

    def post_request(self, url, data):
        resp = requests.post(url, data=data, headers=self.headers, auth=self.auth)
        assert resp.status_code == 201, f'Bad Request Code: {resp.status_code}'
        resp_content_dict = json.loads(resp.content)
        return resp_content_dict

    def get_request(self, url, data):
        resp = requests.post(url, data=data, headers=self.headers, auth=self.auth)
        assert resp.status_code == 201, f'Bad Request Code: {resp.status_code}'
        resp_content_dict = json.loads(resp.content)
        return resp_content_dict

