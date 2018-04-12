/**
 * 配置项，提供全局使用各项配置 amd模块，使用requirejs载入
 */
define(function() {
  this.host = '' 
  return {
    host: this.host,    // 系统地址
    /**
	 * api 接口提供与服务器异步交互地址
	 * 
	 */
    api: {
      login: this.host + '/operate/admin/login', // 登录服务
      logout: this.host + '/operate/admin/logout', // 登出服务
      userInfo: this.host + '/operate/admin/info', // 登录用户信息服务
      resetPwd: this.host + '/operate/admin/reset/password/form', // 修改密码
      genPwd: this.host + '/operate/admin/reset/password/gen', // 重设密码
 
      role: {
        list: this.host + '/operate/admin/ctrl/role/list', // 角色列表
        save: this.host + '/operate/admin/ctrl/role/save', // 新建角色
        update: this.host + '/operate/admin/ctrl/role/update', // 修改角色
        delete: this.host + '/operate/admin/ctrl/role/delete', // 删除角色
        getRoleAuths: this.host + '/operate/admin/ctrl/role/auth/auths', // 获取角色权限
      },
      user: {
        search: this.host + '/operate/admin/search',  // 用户查找
        roles: this.host + '/operate/admin/role/roles', // 当前用户角色
        create: this.host + '/operate/admin/create/form', // 添加用户
        update: this.host + '/operate/admin/update',  // 修改用户
        freeze: this.host + '/operate/admin/freeze',  // 冻结用户
        unfreeze: this.host + '/operate/admin/unfreeze' // 解冻用户
      },
      auth: {
        list: this.host + '/operate/admin/ctrl/auth/list' // 权限列表
      },
      menu: {
        load: this.host + '/operate/system/menu/2.0/load',  // 菜单-加载菜单数据
        save: this.host + '/operate/system/menu/2.0/save',  // 菜单-保存菜单数据
        view: this.host + '/operate/system/menu/2.0/view',  // 菜单-用户菜单获取
      }
    }

  }
})